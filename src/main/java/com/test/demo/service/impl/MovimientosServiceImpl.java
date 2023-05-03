package com.test.demo.service.impl;

import com.test.demo.entities.CuentaEntity;
import com.test.demo.entities.MovimientoEntity;
import com.test.demo.error.DemoError;
import com.test.demo.repository.ClientesRepository;
import com.test.demo.repository.CuentasRepository;
import com.test.demo.repository.MovimientosRepository;
import com.test.demo.service.CuentasService;
import com.test.demo.service.MovimientosService;
import com.test.demo.server.models.*;
import com.test.demo.util.mapper.ClientesMapper;
import com.test.demo.util.mapper.MovimientosMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovimientosServiceImpl implements MovimientosService {

    private final MovimientosMapper movimientosMapper;

    private final MovimientosRepository movimientosRepository;

    private final CuentasService cuentasService;

    private final CuentasRepository cuentasRepository;

    @Override
    public Mono<GetMovimientoResponse> getMovimiento(Long movimientoId) {
        log.info("Inicia obtención del movimiento por id. Id movimiento request: {}",movimientoId);
        return  getMovimientoEntityById(movimientoId)
                .flatMap(movimientoEntity->
                        cuentasService.getCuenta(movimientoEntity.getCuentaId())
                        .map(getCuentaResponse ->
                                movimientosMapper.entityToGetResponse(movimientoEntity, getCuentaResponse)
                        )
                ).doOnSuccess(success->log.info("Movimiento recuperado correctamente"));
    }

    @Override
    public Flux<GetMovimientoResponse> getMovimientos() {
        log.info("Inicia obtención de la lista de movimientos");
        log.warn("Método no implementado");
        throw  DemoError.FE17_METODO_NO_IMPLEMENTADO;
    }

    @Override
    public Mono<Void> patchMovimiento(Mono<PatchMovimientoRequest> patchMovimientoRequest) {
        log.info("Inicia modificación de datos del movimiento");
        log.debug("Datos de movimiento a modificar, request: {}", patchMovimientoRequest);
        log.warn("Método no implementado");
        throw  DemoError.FE17_METODO_NO_IMPLEMENTADO;
    }

    @Override
    public Mono<Void> postMovimiento(Mono<PostMovimientoRequest> postMovimientoRequest) {
        return postMovimientoRequest.flatMap(movimientoARealizar->
                    cuentasRepository.findById(movimientoARealizar.getCuentaId())
                    .doOnError(error->log.error("Error al recuperar la cuenta a la que se realizar el movimiento, detalle: {}", error.getMessage()))
                    .switchIfEmpty(Mono.error(DemoError.FE08_NO_EXISTE_CUENTA_PARA_MOVIMIENTO))
                    .flatMap(cuentaEntity ->
                            permiteRealizarMovimiento(cuentaEntity.getSaldoInicial().doubleValue(), movimientoARealizar)
                            .flatMap(ok->
                                    guardarMovimiento(cuentaEntity,movimientoARealizar)
                            )
                    )
                ).doOnSuccess(success->log.info("Movimiento guardado correctamente"))
                .then();
    }

    @Override
    public Mono<Void> putMovimiento(Mono<PutMovimientoRequest> putMovimientoRequest) {
        log.info("Inicia modificación de datos del movimiento");
        log.debug("Movimiento a modificar, request: {}", putMovimientoRequest);
        log.warn("Método no implementado");
        throw  DemoError.FE17_METODO_NO_IMPLEMENTADO;
    }

    @Override
    public Mono<Void> deleteMovimiento(Long movimientoId) {
        log.info("Inicia la eliminación del movimiento. Id movimiento request: {}", movimientoId);
        return getMovimientoEntityById(movimientoId)
                .flatMap(movimientoEntity -> {
                    movimientoEntity.setEstado(Boolean.FALSE);
                    return movimientosRepository.save(movimientoEntity)
                            .doOnError(error->log.error("Error al eliminar el movimiento, detalle: {}", error.getMessage()))
                            .onErrorResume(error->Mono.error(DemoError.FE12_ELIMINACION_ERROR));
                })
                .doOnSuccess(success->log.info("Movimiento eliminado correctamente"))
                .then();
    }

    private Mono<MovimientoEntity> getMovimientoEntityById(Long movimientoId) {
        log.info("Devuelve movimiento por id: {}", movimientoId);
        return movimientosRepository.findByMovimientoId(movimientoId)
                .doOnError(error->log.error("Error al recuperar el movimiento por id, detalle: {}", error.getMessage()))
                .switchIfEmpty(Mono.error(DemoError.FE06_MOVIMIENTO_POR_ID_NO_ENCONTRADO));
    }

    private Mono<Boolean> verificarFechaValida(PostMovimientoRequest movimientoARealizar) {
        return movimientosRepository.findUltimoMovimientoByCuentaId(movimientoARealizar.getCuentaId())
                .flatMap(movimientoEntity ->{
                        if(Boolean.TRUE.equals(movimientoEntity.getFecha().isAfter(movimientoARealizar.getFecha()))){
                            return Mono.error(DemoError.FE09_FECHA_INVALIDA_MOVIMIENTO);
                        }
                        log.info("fecha válida del movimiento");
                        return Mono.just(Boolean.TRUE);
                })
                .switchIfEmpty(Mono.just(Boolean.TRUE));
    }

    private Mono<Boolean> permiteRealizarMovimiento(Double saldoDisponible, PostMovimientoRequest movimientoARealizar) {
        return verificarFechaValida(movimientoARealizar)
                .flatMap(ok->
                    permiteRealizarDebitoAcredito(saldoDisponible, movimientoARealizar)
                );
    }

    private Mono<Boolean> permiteRealizarDebitoAcredito(Double saldoDisponible, PostMovimientoRequest movimientoARealizar) {
        if(Objects.equals(movimientoARealizar.getTipo(),TipoMovimiento.DEBITO)
                && saldoDisponible.doubleValue()<movimientoARealizar.getValor().doubleValue()){
            return Mono.error(DemoError.FE10_VALOR_DEBITO_INVALIDO);
        }
        log.info("Permite realizar el débito o crédito");
        return Mono.just(Boolean.TRUE);
    }

    private Mono<Void> guardarMovimiento(CuentaEntity cuentaEntity, PostMovimientoRequest movimientoARealizar) {
        return movimientosRepository.save(movimientosMapper.postRequestToEntity(movimientoARealizar, cuentaEntity.getSaldoInicial()))
                .doOnError(error->log.error("Error al guardar el movimiento, detalle: {}", error.getMessage()))
                .flatMap(movimientoEntity -> {
                    if(Objects.equals(movimientoARealizar.getTipo(),TipoMovimiento.CREDITO)){
                        cuentaEntity.setSaldoInicial(movimientoEntity.getSaldo().add(movimientoEntity.getValor()));
                    }
                    else{
                        cuentaEntity.setSaldoInicial(movimientoEntity.getSaldo().subtract(movimientoEntity.getValor()));
                    }
                    return cuentasRepository.save(cuentaEntity)
                            .doOnError(error->log.error("Error al actualizar el saldo de cuenta, detalle: {}", error.getMessage()));
                }).then();
    }
}