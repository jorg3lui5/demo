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
        return getCuentaEntityById(cuentaId)
                .flatMap(cuentaEntity->
                        clientesService.getCliente(cuentaEntity.getClienteId())
                        .map(getClienteResponse ->
                                cuentasMapper.entityToGetResponse(cuentaEntity, getClienteResponse)
                        )
                );
    }

    @Override
    public Flux<GetCuentaResponse> getCuentas() {
        return cuentasRepository.findAll()
                .flatMap(cuentaEntity->
                        clientesService.getCliente(cuentaEntity.getClienteId())
                        .map(getClienteResponse ->
                                cuentasMapper.entityToGetResponse(cuentaEntity, getClienteResponse)
                        )
                );
    }

    @Override
    public Mono<Void> postCuenta(Mono<PostCuentaRequest> postCuentaRequest) {
        return postCuentaRequest.flatMap(request->
                    clientesRepository.findById(request.getClienteId())
                    .switchIfEmpty(Mono.error(DemoError.FE04_NO_EXISTE_CLIENTE_PARA_CUENTA))
                    .flatMap(clienteEntity ->
                            cuentasRepository.save(cuentasMapper.postRequestToEntity(request))
                    )
                ).then();
    }

    @Override
    public Mono<Void> putCuenta(Mono<PutCuentaRequest> putCuentaRequest) {
        return putCuentaRequest.flatMap(request->
                    getCuentaEntityById(request.getCuentaId())
                    .flatMap(cuentaEntity ->
                            verificarCuentaSinMovimientos(cuentaEntity.getCuentaId())
                            .flatMap(ok->
                                    clientesRepository.findById(request.getClienteId())
                                    .switchIfEmpty(Mono.error(DemoError.FE04_NO_EXISTE_CLIENTE_PARA_CUENTA))
                                    .flatMap(clienteEntity ->
                                            cuentasRepository.save(cuentasMapper.putRequestToEntity(request))
                                    )
                            )

                    )
                ).then();
    }

    @Override
    public Mono<Void> patchCuenta(Mono<PatchCuentaRequest> patchCuentaRequest) {
        return patchCuentaRequest.flatMap(request->
                    getCuentaEntityById(request.getCuentaId())
                    .flatMap(cuentaEntity->
                            verificarCuentaSinMovimientos(cuentaEntity.getCuentaId())
                            .flatMap(ok ->
                                    cuentasRepository.save(cuentasMapper.patchRequestToEntity(cuentaEntity,request))
                            )
                    )
                ).then();
    }

    @Override
    public Flux<ReporteCuentasClienteResponse> getReporteCuentasCliente(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        return clientesRepository.findByClienteId(clienteId)
                .flatMapMany(clienteEntity ->
                        cuentasRepository.findByClienteId(clienteId)
                        .switchIfEmpty(Mono.error(DemoError.FE11_CLIENTE_SIN_CUENTAS))
                        .flatMap(cuentaEntity ->
                                movimientosRepository.findMovimientosByCuentaId(cuentaEntity.getCuentaId())
                                .map(movimientoEntity ->
                                        cuentasMapper.entityToReporteCuentasClienteResponse(movimientoEntity, cuentaEntity, clienteEntity)
                                )
                        )
                );

    }

    private Mono<CuentaEntity> getCuentaEntityById(Long cuentaId) {
        return cuentasRepository.findByCuentaId(cuentaId)
                .switchIfEmpty(Mono.error(DemoError.FE05_CUENTA_POR_ID_NO_ENCONTRADA));
    }

    private Mono<Boolean> verificarCuentaSinMovimientos(Long cuentaId) {
        return movimientosRepository.findByCuentaId(cuentaId)
                .collectList()
                .flatMap(lstmovimiento -> {
                    if(lstmovimiento==null || lstmovimiento.isEmpty()){
                        return Mono.just(Boolean.TRUE);
                    }
                    return Mono.error(DemoError.FE07_CUENTA_NO_MODIFICADA_EXISTEN_MOVIMIENTOS);
                });
    }




}