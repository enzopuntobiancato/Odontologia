package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi", uses = {PrivilegioMapper.class, RolUsuarioMapper.class, EnumMapper.class, DocumentoMapper.class})
public interface UsuarioMapper {
    @Mapping(target = "imagenId", source = "imagen.id")
    UsuarioDTO fromUsuario(Usuario usuario);

    Usuario fromUsuarioDTO(UsuarioDTO usuarioDTO);

    @Mapping(target = "roles", ignore = true)
    void updateFromDTO(UsuarioDTO dto, @MappingTarget Usuario usuario);

    @Mappings({
            @Mapping(target = "apellido", expression = "java(source.retrieveFirstPerson().getApellido())"),
            @Mapping(target = "nombre", expression = "java(source.retrieveFirstPerson().getNombre())"),
            @Mapping(target = "fechaNacimiento", expression = "java(source.retrieveFirstPerson().getFechaNacimiento())", resultType = DocumentoDTO.class),
            @Mapping(target = "imagenId", source = "imagen.id")
    })
    UsuarioViewEditDTO toUsuarioViewDTO(Usuario source);

    @Mapping(target = "roles", ignore = true)
    void updateFromDTO(UsuarioViewEditDTO dto, @MappingTarget Usuario usuario);
}
