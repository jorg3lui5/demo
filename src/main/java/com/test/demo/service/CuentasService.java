package com.test.demo.service;

import com.test.demo.server.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public interface CuentasService {

    Mono<GetCuentaResponse> getCuenta(Long cuentaId);

    Flux<GetCuentaResponse> getCuentas();

    Mono<Void> patchCuenta(Mono<PatchCuentaRequest> patchCuentaRequest);

    Mono<Void> postCuenta(Mono<PostCuentaRequest> postCuentaRequest);

    Mono<Void> putCuenta(Mono<PutCuentaRequest> putCuentaRequest);

    Flux<ReporteCuentasClienteResponse> getReporteCuentasCliente(Long clienteId,
                                                                 LocalDate fechaInicio,
                                                                 LocalDate fechaFin);
}
