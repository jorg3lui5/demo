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
        log.info("Inicia Obtención del cliente por id. Id cliente request: {}",clienteId);
        return clientesRepository.findByClienteId(clienteId)
                .doOnError(error->log.error("Error al recuperar el cliente por id, detalle: {}", error.getMessage()))
                .switchIfEmpty(Mono.error(DemoError.FE02_CLIENTE_POR_ID_NO_ENCONTRADO))
                .map(entity->clientesMapper.entityToGetResponse(entity))
                .doOnSuccess(success->log.info("Cliente recuperado correctamente"));
    }

    @Override
    public Flux<GetClienteResponse> getClientes() {
        log.info("Inicia obtención de la lista de clientes");
        return clientesRepository.findAllCliente()
                .doOnError(error->log.error("Error al recuperar los clientes, detalle: {}", error.getMessage()))
                .switchIfEmpty(Mono.error(DemoError.FE03_ClIENTES_NO_ENCONTRADOS))
                .map(entity->clientesMapper.entityToGetResponse(entity));

    }

    @Override
    public Mono<Void> postCliente(Mono<PostClienteRequest> postClienteRequest) {
        log.info("Inicia el guardado del cliente.");
        log.debug("Guarda cliente Request: {}", postClienteRequest);
        return postClienteRequest.flatMap(request->
                personasRepository.save(personasMapper.postRequestToEntity(request))
                .doOnError(error->log.error("Error al guardar la persona, detalle: {}", error.getMessage()))
                .flatMap(personaEntity ->
                    clientesRepository.save(clientesMapper.postRequestToEntity(request,personaEntity.getPersonaId()))
                    .doOnError(error->log.error("Error al guardar el cliente, detalle: {}", error.getMessage()))
                )
        )
        .doOnSuccess(success->log.info("Cliente guardado correctamente"))
        .then();
    }

    @Override
    public Mono<Void> putCliente(Mono<PutClienteRequest> putClienteRequest) {
        log.info("Inicia la modificación completa de cliente.");
        log.debug("Modifica cliente Request: {}", putClienteRequest);
        return putClienteRequest.flatMap(request->
                    getClienteEntityById(request.getClienteId())
                    .flatMap(clienteEntity ->
                            Mono.zip(
                                    personasRepository.save(personasMapper.putRequestToEntity(request,clienteEntity.getPersonaId()))
                                    .doOnError(error->log.error("Error al modificar la persona, detalle: {}", error.getMessage())),
                                    clientesRepository.save(clientesMapper.putRequestToEntity(request, clienteEntity.getPersonaId()))
                                    .doOnError(error->log.error("Error al modificar el cliente, detalle: {}", error.getMessage()))
                            )
                    )
                )
                .doOnSuccess(success->log.info("Cliente modificado correctamente"))
                .then();
    }

    @Override
    public Mono<Void> patchCliente(Mono<PatchClienteRequest> patchClienteRequest) {
        log.info("Inicia la modificación de datos de cliente.");
        log.debug("Modifica datos del cliente Request: {}", patchClienteRequest);
        return patchClienteRequest.flatMap(request->
                getClienteEntityById(request.getClienteId())
                .flatMap(clienteEntity->
                        personasRepository.findByPersonaId(clienteEntity.getPersonaId())
                        .doOnError(error->log.error("Error al modificar el cliente, detalle: {}", error.getMessage()))
                        .flatMap(personaEntity ->
                            Mono.zip(
                                    personasRepository.save(personasMapper.patchRequestToEntity(personaEntity,request))
                                    .doOnError(error->log.error("Error al modificar los datos de la persona, detalle: {}", error.getMessage())),
                                    clientesRepository.save(clientesMapper.patchRequestToEntity(clienteEntity,request))
                                    .doOnError(error->log.error("Error al modificar los datos del cliente, detalle: {}", error.getMessage()))
                            )
                        )

                )
                .doOnSuccess(success->log.info("Datos de cliente modificados correctamente"))
                .then()
        );
    }

    @Override
    public Mono<Void> deleteCliente(Long clienteId) {
        log.info("Inicia la eliminación del cliente. Id cliente request: {}", clienteId);
        return getClienteEntityById(clienteId)
                .flatMap(clienteEntity -> {
                    clienteEntity.setEstado(Boolean.FALSE);
                    return clientesRepository.save(clienteEntity)
                            .doOnError(error->log.error("Error al eliminar el cliente, detalle: {}", error.getMessage()))
                            .onErrorResume(error->Mono.error(DemoError.FE12_ELIMINACION_ERROR));
                })
                .doOnSuccess(success->log.info("cliente eliminado correctamente"))
                .then();
    }

    public Mono<ClienteEntity> getClienteEntityById(Long clienteId) {
        log.info("Devuelve cliente por id: {}", clienteId);
        return clientesRepository.findById(clienteId)
                .doOnError(error->log.error("Error al recuperar el cliente por id, detalle: {}", error.getMessage()))
                .switchIfEmpty(Mono.error(DemoError.FE02_CLIENTE_POR_ID_NO_ENCONTRADO));
    }


}