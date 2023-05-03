package com.test.demo.api.delegate;

import com.test.demo.service.MovimientosService;
import com.test.demo.server.MovimientosApiDelegate;
import com.test.demo.server.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovimientosApiDelegateImpl implements MovimientosApiDelegate {

    private final MovimientosService movimientosService;

    @Override
    public Mono<ResponseEntity<GetMovimientoResponse>> getMovimiento(Long movimientoId,
                                                                     ServerWebExchange exchange) {

        return movimientosService.getMovimiento(movimientoId)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<GetMovimientoResponse>>> getMovimientos(ServerWebExchange exchange) {
        return Mono.just(movimientosService.getMovimientos())
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> patchMovimiento(Mono<PatchMovimientoRequest> patchMovimientoRequest,
                                                      ServerWebExchange exchange) {

        return movimientosService.patchMovimiento(patchMovimientoRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> postMovimiento(Mono<PostMovimientoRequest> postMovimientoRequest,
                                                     ServerWebExchange exchange) {

        return movimientosService.postMovimiento(postMovimientoRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> putMovimiento(Mono<PutMovimientoRequest> putMovimientoRequest,
                                                    ServerWebExchange exchange) {

        return movimientosService.putMovimiento(putMovimientoRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteMovimiento(Long movimientoId,
                                                ServerWebExchange exchange) {

        return movimientosService.deleteMovimiento(movimientoId)
                .map(ResponseEntity::ok);

    }

}
