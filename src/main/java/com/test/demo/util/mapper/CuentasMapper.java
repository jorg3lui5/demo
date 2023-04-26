package com.test.demo.util.mapper;

import com.test.demo.dto.ClienteEntityDTO;
import com.test.demo.entities.ClienteEntity;
import com.test.demo.entities.CuentaEntity;
import com.test.demo.entities.MovimientoEntity;
import com.test.demo.entities.PersonaEntity;
import com.test.demo.server.models.*;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface CuentasMapper {

    @Mapping(target = "cuentaId", source = "entity.cuentaId")
    @Mapping(target = "numero", source = "entity.numero")
    @Mapping(target = "tipo", source = "entity.tipo")
    @Mapping(target = "saldoInicial", source = "entity.saldoInicial")
    @Mapping(target = "estado", source = "entity.estado")
    @Mapping(target = "cliente", source = "cliente")
    GetCuentaResponse entityToGetResponse(CuentaEntity entity, GetClienteResponse cliente);

    @Mapping(target = "cuentaId", ignore = true)
    @Mapping(target = "numero", source = "numero")
    @Mapping(target = "tipo", source = "tipo")
    @Mapping(target = "saldoInicial", source = "saldoInicial")
    @Mapping(target = "estado", expression = "java(Boolean.TRUE)")
    @Mapping(target = "clienteId", source = "clienteId")
    CuentaEntity postRequestToEntity(PostCuentaRequest request);

    @Mapping(target = "cuentaId", source = "cuentaId")
    @Mapping(target = "numero", source = "numero")
    @Mapping(target = "tipo", source = "tipo")
    @Mapping(target = "saldoInicial", source = "saldoInicial")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "clienteId", source = "clienteId")
    CuentaEntity putRequestToEntity(PutCuentaRequest request);

    @Mapping(target = "cuentaId", ignore = true)
    @Mapping(target = "numero", source = "numero")
    @Mapping(target = "tipo", source = "tipo")
    @Mapping(target = "saldoInicial", source = "saldoInicial")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "clienteId", ignore = true)
    CuentaEntity patchRequestToEntity(@MappingTarget CuentaEntity entity, PatchCuentaRequest request);

    @Mapping(target = "fecha", source = "movimiento.fecha")
    @Mapping(target = "cliente", source = "cliente.nombre")
    @Mapping(target = "numeroCuenta", source = "cuenta.numero")
    @Mapping(target = "tipo", source = "cuenta.tipo")
    @Mapping(target = "saldoInicial", source = "movimiento.saldo")
    @Mapping(target = "estado", source = "movimiento.estado")
    @Mapping(target = "movimiento", source = "movimiento", qualifiedByName = "getValorMovimiento")
    @Mapping(target = "saldoDisponible", source = "movimiento", qualifiedByName = "getSaldoDisponible")
    ReporteCuentasClienteResponse entityToReporteCuentasClienteResponse(MovimientoEntity movimiento, CuentaEntity cuenta, ClienteEntityDTO cliente);

    @Named("getValorMovimiento")
    static String getValorMovimiento(MovimientoEntity movimiento) {
        if (movimiento.getTipo().equals(TipoMovimiento.DEBITO.getValue())) {
            return "-"+movimiento.getValor();
        }
        return String.valueOf(movimiento.getValor());
    }

    @Named("getSaldoDisponible")
    static Double getSaldoDisponible(MovimientoEntity movimiento) {
        if (movimiento.getTipo().equals(TipoMovimiento.DEBITO.getValue())) {
            return movimiento.getSaldo().subtract(movimiento.getValor()).doubleValue();
        }
        return movimiento.getSaldo().add(movimiento.getValor()).doubleValue();
    }
}
