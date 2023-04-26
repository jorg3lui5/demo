package com.test.demo.repository;

import com.test.demo.entities.PersonaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonasRepository extends ReactiveCrudRepository<PersonaEntity, Long> {

    @Query("select * from persona WHERE persona_id = :personaId")
    Mono<PersonaEntity> findByPersonaId(Long personaId);

    @Query("select * from persona WHERE persona_id = :personaId")
    Mono<PersonaEntity> findByClienteId(Long clienteId);
}
