package com.test.demo.util.mapper;

import com.test.demo.dto.ClienteEntityDTO;
import com.test.demo.entities.CuentaEntity;
import com.test.demo.entities.MovimientoEntity;
import com.test.demo.server.models.*;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface MovimientosMapper {

    @Mapping(target = "movimientoId", source = "entity.movimientoId")
    @Mapping(target = "fecha", source = "entity.fecha")
    @Mapping(target = "tipo", source = "entity.tipo")
    @Mapping(target = "valor", source = "entity.valor")
    @Mapping(target = "saldo", source = "entity.saldo")
    @Mapping(target = "estado", source = "entity.estado")
    @Mapping(target = "cuenta", source = "cuenta")
    GetMovimientoResponse entityToGetResponse(MovimientoEntity entity, GetCuentaResponse cuenta);

    @Mapping(target = "movimientoId", ignore = true)
    @Mapping(target = "fecha", source = "request.fecha")
    @Mapping(target = "tipo", source = "request.tipo")
    @Mapping(target = "valor", source = "request.valor")
    @Mapping(target = "saldo", source = "saldo")
    @Mapping(target = "estado", expression = "java(Boolean.TRUE)")
    @Mapping(target = "cuentaId", source = "request.cuentaId")
    MovimientoEntity postRequestToEntity(PostMovimientoRequest request, BigDecimal saldo);


}
