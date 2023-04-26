package com.test.demo.util.mapper;

import com.test.demo.dto.ClienteEntityDTO;
import com.test.demo.entities.ClienteEntity;
import com.test.demo.server.models.GetClienteResponse;
import com.test.demo.server.models.PatchClienteRequest;
import com.test.demo.server.models.PostClienteRequest;
import com.test.demo.server.models.PutClienteRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface ClientesMapper {

    @Mapping(target = "clienteId", source = "clienteId")
    @Mapping(target = "identificacion", source = "identificacion")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "genero", source = "genero")
    @Mapping(target = "edad", source = "edad")
    @Mapping(target = "direccion", source = "direccion")
    @Mapping(target = "telefono", source = "telefono")
    @Mapping(target = "estado", source = "estado")
    GetClienteResponse entityToGetResponse(ClienteEntityDTO entity);

    @Mapping(target = "clienteId", ignore = true)
    @Mapping(target = "contrasenia", source = "request.contrasenia")
    @Mapping(target = "estado", expression = "java(Boolean.TRUE)")
    @Mapping(target = "personaId", source = "personaId")
    ClienteEntity postRequestToEntity(PostClienteRequest request, Long personaId);

    @Mapping(target = "clienteId", source = "request.clienteId")
    @Mapping(target = "contrasenia", source = "request.contrasenia")
    @Mapping(target = "estado", source = "request.estado")
    @Mapping(target = "personaId", source = "personaId")
    ClienteEntity putRequestToEntity(PutClienteRequest request, Long personaId);

    @Mapping(target = "clienteId",ignore = true)
    @Mapping(target = "contrasenia", source = "contrasenia")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "personaId", ignore = true)
    ClienteEntity patchRequestToEntity(@MappingTarget ClienteEntity entity, PatchClienteRequest request);

}
