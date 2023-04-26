package com.test.demo.repository;

import com.test.demo.dto.ClienteEntityDTO;
import com.test.demo.entities.MovimientoEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MovimientosRepository extends ReactiveCrudRepository<MovimientoEntity, Long> {

	Mono<MovimientoEntity> findByMovimientoId(Long movimientoId);

	Flux<MovimientoEntity> findByCuentaId(Long cuentaId);

	@Query("select * from movimiento m WHERE m.cuenta_id = :cuentaId order by m.fecha desc limit 1")
	Mono<MovimientoEntity> findUltimoMovimientoByCuentaId(Long cuentaId);

	@Query("select * from movimiento m WHERE m.cuenta_id = :cuentaId order by m.fecha desc, m.movimiento_id desc")
	Flux<MovimientoEntity> findMovimientosByCuentaId(Long cuentaId);
}
