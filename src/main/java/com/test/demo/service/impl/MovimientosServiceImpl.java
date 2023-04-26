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
        return  getMovimientoEntityById(movimientoId)
                .flatMap(movimientoEntity->
                        cuentasService.getCuenta(movimientoEntity.getCuentaId())
                        .map(getCuentaResponse ->
                                movimientosMapper.entityToGetResponse(movimientoEntity, getCuentaResponse)
                        )
                );
    }

    @Override
    public Flux<GetMovimientoResponse> getMovimientos() {
        return null;
    }

    @Override
    public Mono<Void> patchMovimiento(Mono<PatchMovimientoRequest> patchMovimientoRequest) {
        return null;
    }

    @Override
    public Mono<Void> postMovimiento(Mono<PostMovimientoRequest> postMovimientoRequest) {
        return postMovimientoRequest.flatMap(movimientoARealizar->
                    cuentasRepository.findById(movimientoARealizar.getCuentaId())
                    .switchIfEmpty(Mono.error(DemoError.FE08_NO_EXISTE_CUENTA_PARA_MOVIMIENTO))
                    .flatMap(cuentaEntity ->
                            permiteRealizarMovimiento(cuentaEntity.getSaldoInicial().doubleValue(), movimientoARealizar)
                            .flatMap(ok->
                                    guardarMovimiento(cuentaEntity,movimientoARealizar)
                            )
                    )
                ).then();
    }

    @Override
    public Mono<Void> putMovimiento(Mono<PutMovimientoRequest> putMovimientoRequest) {
        return null;
    }

    private Mono<MovimientoEntity> getMovimientoEntityById(Long movimientoId) {
        return movimientosRepository.findByMovimientoId(movimientoId)
                .switchIfEmpty(Mono.error(DemoError.FE06_MOVIMIENTO_POR_ID_NO_ENCONTRADO));
    }

    private Mono<Boolean> verificarFechaValida(PostMovimientoRequest movimientoARealizar) {
        return movimientosRepository.findUltimoMovimientoByCuentaId(movimientoARealizar.getCuentaId())
                .flatMap(movimientoEntity ->{
                        if(Boolean.TRUE.equals(movimientoEntity.getFecha().isAfter(movimientoARealizar.getFecha()))){
                            return Mono.error(DemoError.FE09_FECHA_INVALIDA_MOVIMIENTO);
                        }
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
        return Mono.just(Boolean.TRUE);
    }

    private Mono<Void> guardarMovimiento(CuentaEntity cuentaEntity, PostMovimientoRequest movimientoARealizar) {
        return movimientosRepository.save(movimientosMapper.postRequestToEntity(movimientoARealizar, cuentaEntity.getSaldoInicial()))
                .flatMap(movimientoEntity -> {
                    if(Objects.equals(movimientoARealizar.getTipo(),TipoMovimiento.CREDITO)){
                        cuentaEntity.setSaldoInicial(movimientoEntity.getSaldo().add(movimientoEntity.getValor()));
                    }
                    else{
                        cuentaEntity.setSaldoInicial(movimientoEntity.getSaldo().subtract(movimientoEntity.getValor()));
                    }
                    return cuentasRepository.save(cuentaEntity);
                }).then();
    }
}