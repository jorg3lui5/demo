package com.test.demo.service;

import com.test.demo.server.models.GetClienteResponse;
import com.test.demo.server.models.PatchClienteRequest;
import com.test.demo.server.models.PostClienteRequest;
import com.test.demo.server.models.PutClienteRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface ClientesService {

    Mono<GetClienteResponse> getCliente(Long clienteId);

    Flux<GetClienteResponse> getClientes();

    Mono<Void> patchCliente(Mono<PatchClienteRequest> patchClienteRequest);

    Mono<Void> postCliente(Mono<PostClienteRequest> postClienteRequest);

    Mono<Void> putCliente(Mono<PutClienteRequest> putClienteRequest);
}
