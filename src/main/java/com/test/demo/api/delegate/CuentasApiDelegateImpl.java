package com.test.demo.api.delegate;

import com.test.demo.server.ApiUtil;
import com.test.demo.service.CuentasService;
import com.test.demo.server.CuentasApiDelegate;
import com.test.demo.server.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class CuentasApiDelegateImpl implements CuentasApiDelegate {

    private final CuentasService cuentasService;

    @Override
    public Mono<ResponseEntity<GetCuentaResponse>> getCuenta(Long cuentaId,
                                                             ServerWebExchange exchange) {

        return cuentasService.getCuenta(cuentaId)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<GetCuentaResponse>>> getCuentas(ServerWebExchange exchange) {
        return Mono.just(cuentasService.getCuentas())
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> patchCuenta(Mono<PatchCuentaRequest> patchCuentaRequest,
                                                  ServerWebExchange exchange) {

        return cuentasService.patchCuenta(patchCuentaRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> postCuenta(Mono<PostCuentaRequest> postCuentaRequest,
                                                 ServerWebExchange exchange) {

        return cuentasService.postCuenta(postCuentaRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> putCuenta(Mono<PutCuentaRequest> putCuentaRequest,
                                                ServerWebExchange exchange) {

        return cuentasService.putCuenta(putCuentaRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<ReporteCuentasClienteResponse>>> getReporteCuentasCLiente(Long clienteId,
                                                                                              LocalDate fechaInicio,
                                                                                              LocalDate fechaFin,
                                                                                              ServerWebExchange exchange) {

        return Mono.just(cuentasService.getReporteCuentasCliente(clienteId, fechaInicio, fechaFin))
                .map(ResponseEntity::ok);
    }

}
