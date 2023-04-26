package com.test.demo.repository;

import com.test.demo.entities.CuentaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CuentasRepository extends ReactiveCrudRepository<CuentaEntity, Long> {

	Mono<CuentaEntity> findByCuentaId(Long cuentaId);

	@Query("select * from cuenta c WHERE c.cliente_id = :clienteId ")
	Flux<CuentaEntity> findByClienteId(Long clienteId);
}
