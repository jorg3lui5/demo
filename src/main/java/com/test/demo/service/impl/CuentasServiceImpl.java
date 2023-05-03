package com.test.demo.service.impl;

import com.test.demo.entities.CuentaEntity;
import com.test.demo.entities.MovimientoEntity;
import com.test.demo.error.DemoError;
import com.test.demo.repository.ClientesRepository;
import com.test.demo.repository.CuentasRepository;
import com.test.demo.repository.MovimientosRepository;
import com.test.demo.service.ClientesService;
import com.test.demo.service.CuentasService;
import com.test.demo.server.models.*;
import com.test.demo.util.mapper.CuentasMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuentasServiceImpl implements CuentasService {

    private final CuentasMapper cuentasMapper;

    private final CuentasRepository cuentasRepository;

    private final ClientesRepository clientesRepository;

    private final MovimientosRepository movimientosRepository;

    private final ClientesService clientesService;

    @Override
    public Mono<GetCuentaResponse> getCuenta(Long cuentaId) {
        log.info("Inicia Obtención de la cuenta por id. Id cuenta request: {}",cuentaId);
        return getCuentaEntityById(cuentaId)
                .flatMap(cuentaEntity->
                        clientesService.getCliente(cuentaEntity.getClienteId())
                        .map(getClienteResponse ->
                                cuentasMapper.entityToGetResponse(cuentaEntity, getClienteResponse)
                        )
                ).doOnSuccess(success->log.info("Cuenta recuperado correctamente"));
    }

    @Override
    public Flux<GetCuentaResponse> getCuentas() {
        log.info("Inicia obtención de la lista de cuentas");
        return cuentasRepository.findAll()
                .doOnError(error->log.error("Error al recuperar las cuentas, detalle: {}", error.getMessage()))
                .flatMap(cuentaEntity->
                        clientesService.getCliente(cuentaEntity.getClienteId())
                        .map(getClienteResponse ->
                                cuentasMapper.entityToGetResponse(cuentaEntity, getClienteResponse)
                        )
                );
    }

    @Override
    public Mono<Void> postCuenta(Mono<PostCuentaRequest> postCuentaRequest) {
        log.info("Inicia el guardado de la cuenta.");
        log.debug("Guarda cuenta Request: {}", postCuentaRequest);
        return postCuentaRequest.flatMap(request->
                    clientesRepository.findById(request.getClienteId())
                    .doOnError(error->log.error("Error al recuperar al cliente de la cuenta, detalle: {}", error.getMessage()))
                    .switchIfEmpty(Mono.error(DemoError.FE04_NO_EXISTE_CLIENTE_PARA_CUENTA))
                    .flatMap(clienteEntity ->
                            cuentasRepository.save(cuentasMapper.postRequestToEntity(request))
                            .doOnError(error->log.error("Error al guardar la cuenta, detalle: {}", error.getMessage()))
                    )
                )
                .doOnSuccess(success->log.info("Cuenta guardado correctamente"))
                .then();
    }

    @Override
    public Mono<Void> putCuenta(Mono<PutCuentaRequest> putCuentaRequest) {
        log.info("Inicia la modificación completa de la cuenta.");
        log.debug("Modifica cuenta Request: {}", putCuentaRequest);
        return putCuentaRequest.flatMap(request->
                    getCuentaEntityById(request.getCuentaId())
                    .flatMap(cuentaEntity ->
                            verificarCuentaSinMovimientos(cuentaEntity.getCuentaId())
                            .flatMap(ok->
                                    clientesRepository.findById(request.getClienteId())
                                    .doOnError(error->log.error("Error al recuperar al cliente de la cuenta, detalle: {}", error.getMessage()))
                                    .switchIfEmpty(Mono.error(DemoError.FE04_NO_EXISTE_CLIENTE_PARA_CUENTA))
                                    .flatMap(clienteEntity ->
                                            cuentasRepository.save(cuentasMapper.putRequestToEntity(request))
                                            .doOnError(error->log.error("Error al modificar la cuenta, detalle: {}", error.getMessage()))
                                    )
                            )

                    )
            ).doOnSuccess(success->log.info("Cuenta modificada correctamente"))
            .then();
    }

    @Override
    public Mono<Void> patchCuenta(Mono<PatchCuentaRequest> patchCuentaRequest) {
        log.info("Inicia la modificación de datos de la cuenta.");
        log.debug("Modifica datos de la cuenta Request: {}", patchCuentaRequest);
        return patchCuentaRequest.flatMap(request->
                    getCuentaEntityById(request.getCuentaId())
                    .flatMap(cuentaEntity->
                            verificarCuentaSinMovimientos(cuentaEntity.getCuentaId())
                            .flatMap(ok ->
                                    cuentasRepository.save(cuentasMapper.patchRequestToEntity(cuentaEntity,request))
                                    .doOnError(error->log.error("Error al modificar los datos de la cuenta, detalle: {}", error.getMessage()))
                            )
                    )
                ).doOnSuccess(success->log.info("Datos de la cuenta modificados correctamente"))
                .then();
    }

    @Override
    public Mono<Void> deleteCuenta(Long cuentaId) {
        log.info("Inicia la eliminación de la cuenta. Id cuenta request: {}", cuentaId);
        return getCuentaEntityById(cuentaId)
                .flatMap(cuentaEntity -> {
                    cuentaEntity.setEstado(Boolean.FALSE);
                    return cuentasRepository.save(cuentaEntity)
                            .doOnError(error->log.error("Error al eliminar la cuenta, detalle: {}", error.getMessage()))
                            .onErrorResume(error->Mono.error(DemoError.FE12_ELIMINACION_ERROR));
                })
                .doOnSuccess(success->log.info("Cuenta eliminada correctamente"))
                .then();
    }

    @Override
    public Flux<ReporteCuentasClienteResponse> getReporteCuentasCliente(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Inicia la obtención de reporte de cuentas del cliente con cliente Id: {}, fecha de inicio: {} y fecha de fin: {}", clienteId, fechaInicio, fechaFin);
        return clientesRepository.findByClienteId(clienteId)
                .doOnError(error->log.error("Error al recuperar al cliente de la cuenta, detalle: {}", error.getMessage()))
                .flatMapMany(clienteEntity ->
                        cuentasRepository.findByClienteId(clienteId)
                        .doOnError(error->log.error("Error al recuperar la lista de cuentas del cliente, detalle: {}", error.getMessage()))
                        .switchIfEmpty(Mono.error(DemoError.FE11_CLIENTE_SIN_CUENTAS))
                        .flatMap(cuentaEntity ->
                                movimientosRepository.findMovimientosByCuentaId(cuentaEntity.getCuentaId())
                                .doOnError(error->log.error("Error al recuperar los movimientos de la cuenta, detalle: {}", error.getMessage()))
                                .map(movimientoEntity ->
                                    cuentasMapper.entityToReporteCuentasClienteResponse(movimientoEntity, cuentaEntity, clienteEntity)
                                )
                        )
                );

    }

    private Mono<CuentaEntity> getCuentaEntityById(Long cuentaId) {
        return cuentasRepository.findByCuentaId(cuentaId)
                .doOnError(error->log.error("Error al recuperar la cuenta por id, detalle: {}", error.getMessage()))
                .switchIfEmpty(Mono.error(DemoError.FE05_CUENTA_POR_ID_NO_ENCONTRADA));
    }

    private Mono<Boolean> verificarCuentaSinMovimientos(Long cuentaId) {
        return movimientosRepository.findByCuentaId(cuentaId)
                .doOnError(error->log.error("Error al verificar si la cuenta ya tiene movimientos realizados, detalle: {}", error.getMessage()))
                .collectList()
                .flatMap(lstmovimiento -> {
                    if(lstmovimiento==null || lstmovimiento.isEmpty()){
                        log.warn("La cuenta no tiene movimientos");
                        return Mono.just(Boolean.TRUE);
                    }
                    return Mono.error(DemoError.FE07_CUENTA_NO_MODIFICADA_EXISTEN_MOVIMIENTOS);
                });
    }




}