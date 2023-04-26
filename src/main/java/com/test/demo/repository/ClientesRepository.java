package com.test.demo.repository;

import com.test.demo.dto.ClienteEntityDTO;
import com.test.demo.entities.ClienteEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClientesRepository extends ReactiveCrudRepository<ClienteEntity, Long> {
    @Query("select * from cliente c INNER JOIN persona p ON c.persona_id = p.persona_id WHERE c.cliente_id = :clienteId ")
    Mono<ClienteEntityDTO> findByClienteId(Long clienteId);

    @Query("select * from cliente c INNER JOIN persona p ON c.persona_id = p.persona_id")
    Flux<ClienteEntityDTO> findAllCliente();
}
