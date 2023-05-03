package com.test.demo.service;

import com.test.demo.server.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public interface MovimientosService {

    Mono<GetMovimientoResponse> getMovimiento(Long movimientoId);

    Flux<GetMovimientoResponse> getMovimientos();

    Mono<Void> patchMovimiento(Mono<PatchMovimientoRequest> patchMovimientoRequest);

    Mono<Void> postMovimiento(Mono<PostMovimientoRequest> postMovimientoRequest);

    Mono<Void> putMovimiento(Mono<PutMovimientoRequest> putMovimientoRequest);

    Mono<Void> deleteMovimiento(Long movimientoId);

}
