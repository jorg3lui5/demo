package com.test.demo.service.impl;

import com.test.demo.entities.ClienteEntity;
import com.test.demo.error.DemoError;
import com.test.demo.repository.ClientesRepository;
import com.test.demo.repository.PersonasRepository;
import com.test.demo.service.ClientesService;
import com.test.demo.server.models.GetClienteResponse;
import com.test.demo.server.models.PatchClienteRequest;
import com.test.demo.server.models.PostClienteRequest;
import com.test.demo.server.models.PutClienteRequest;
import com.test.demo.util.mapper.ClientesMapper;
import com.test.demo.util.mapper.PersonasMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientesServiceImpl implements ClientesService {

    private final ClientesMapper clientesMapper;

    private final PersonasMapper personasMapper;

    private final ClientesRepository clientesRepository;

    private final PersonasRepository personasRepository;

    @Override
    public Mono<GetClienteResponse> getCliente(Long clienteId) {
        return clientesRepository.findByClienteId(clienteId)
                .switchIfEmpty(Mono.error(DemoError.FE02_CLIENTE_POR_ID_NO_ENCONTRADO))
                .map(entity->clientesMapper.entityToGetResponse(entity));
    }

    @Override
    public Flux<GetClienteResponse> getClientes() {
        return clientesRepository.findAllCliente()
                .switchIfEmpty(Mono.error(DemoError.FE03_ClIENTES_NO_ENCONTRADOS))
                .map(entity->clientesMapper.entityToGetResponse(entity));
    }

    @Override
    public Mono<Void> postCliente(Mono<PostClienteRequest> postClienteRequest) {
        return postClienteRequest.flatMap(request->
                personasRepository.save(personasMapper.postRequestToEntity(request))
                        .flatMap(personaEntity ->
                            clientesRepository.save(clientesMapper.postRequestToEntity(request,personaEntity.getPersonaId()))
                        )
        ).then();
    }

    @Override
    public Mono<Void> putCliente(Mono<PutClienteRequest> putClienteRequest) {
        return putClienteRequest.flatMap(request->
                    getClienteEntityById(request.getClienteId())
                    .flatMap(clienteEntity ->
                            Mono.zip(
                                    personasRepository.save(personasMapper.putRequestToEntity(request,clienteEntity.getPersonaId())),
                                    clientesRepository.save(clientesMapper.putRequestToEntity(request, clienteEntity.getPersonaId()))
                            )
                    )
                ).then();
    }

    @Override
    public Mono<Void> patchCliente(Mono<PatchClienteRequest> patchClienteRequest) {
        return patchClienteRequest.flatMap(request->
                getClienteEntityById(request.getClienteId())
                .flatMap(clienteEntity->
                        personasRepository.findByPersonaId(clienteEntity.getPersonaId())
                        .flatMap(personaEntity ->
                                Mono.zip(
                                        personasRepository.save(personasMapper.patchRequestToEntity(personaEntity,request)),
                                        clientesRepository.save(clientesMapper.patchRequestToEntity(clienteEntity,request))
                                )
                        )

                ).then()
        );
    }

    public Mono<ClienteEntity> getClienteEntityById(Long clienteId) {
        return clientesRepository.findById(clienteId)
                .switchIfEmpty(Mono.error(DemoError.FE02_CLIENTE_POR_ID_NO_ENCONTRADO));
    }


}