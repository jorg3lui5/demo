package com.test.demo.util.mapper;

import com.test.demo.entities.PersonaEntity;
import com.test.demo.server.models.PatchClienteRequest;
import com.test.demo.server.models.PostClienteRequest;
import com.test.demo.server.models.PutClienteRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface PersonasMapper {

    @Mapping(target = "personaId", ignore = true)
    @Mapping(target = "identificacion", source = "identificacion")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "genero", source = "genero")
    @Mapping(target = "edad", source = "edad")
    @Mapping(target = "direccion", source = "direccion")
    @Mapping(target = "telefono", source = "telefono")
    PersonaEntity postRequestToEntity(PostClienteRequest request);

    @Mapping(target = "personaId", source = "personaId")
    @Mapping(target = "identificacion", source = "request.identificacion")
    @Mapping(target = "nombre", source = "request.nombre")
    @Mapping(target = "genero", source = "request.genero")
    @Mapping(target = "edad", source = "request.edad")
    @Mapping(target = "direccion", source = "request.direccion")
    @Mapping(target = "telefono", source = "request.telefono")
    PersonaEntity putRequestToEntity(PutClienteRequest request, Long personaId);

    @Mapping(target = "personaId", ignore = true)
    @Mapping(target = "identificacion", source = "identificacion")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "genero", source = "genero")
    @Mapping(target = "edad", source = "edad")
    @Mapping(target = "direccion", source = "direccion")
    @Mapping(target = "telefono", source = "telefono")
    PersonaEntity patchRequestToEntity(@MappingTarget PersonaEntity entity, PatchClienteRequest request);
}
