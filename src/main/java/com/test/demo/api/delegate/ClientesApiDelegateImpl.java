package com.test.demo.api.delegate;

import com.test.demo.service.ClientesService;
import com.test.demo.server.ClientesApiDelegate;
import com.test.demo.server.models.GetClienteResponse;
import com.test.demo.server.models.PatchClienteRequest;
import com.test.demo.server.models.PostClienteRequest;
import com.test.demo.server.models.PutClienteRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientesApiDelegateImpl implements ClientesApiDelegate {

    private final ClientesService clientesService;

    @Override
    public Mono<ResponseEntity<GetClienteResponse>> getCliente(Long clienteId,
                                                               ServerWebExchange exchange) {

        return clientesService.getCliente(clienteId)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<GetClienteResponse>>> getClientes(ServerWebExchange exchange) {
        return Mono.just(clientesService.getClientes())
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> patchCliente(Mono<PatchClienteRequest> patchClienteRequest,
                                                    ServerWebExchange exchange) {

        return clientesService.patchCliente(patchClienteRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> postCliente(Mono<PostClienteRequest> postClienteRequest,
                                                   ServerWebExchange exchange) {

        return clientesService.postCliente(postClienteRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> putCliente(Mono<PutClienteRequest> putClienteRequest,
                                                  ServerWebExchange exchange) {

        return clientesService.putCliente(putClienteRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCliente(Long clienteId,
                                             ServerWebExchange exchange) {
        return clientesService.deleteCliente(clienteId)
                .map(ResponseEntity::ok);

    }


}
