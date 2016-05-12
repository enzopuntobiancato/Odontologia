package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.UsuarioDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi", uses = {PrivilegioMapper.class, RollMapper.class})
public interface UsuarioMapper {

    @Mappings({
            @Mapping(source = "rol.nombre", target = "rol"),
            @Mapping(source = "rol.privilegios", target = "permisos"),
            @Mapping(target = "firstLogin", expression = "java(usuario.getUltimaConexion() == null)"),
            @Mapping(target = "imagenId", source = "imagen.id")
    })
    UsuarioLogueadoDTO toUsuarioLogueadoDTO(Usuario usuario);

    @Mapping(target = "imagenId", source = "imagen.id")
    UsuarioDTO fromUsuario(Usuario usuario);

    Usuario fromUsuarioDTO(UsuarioDTO usuarioDTO);

    @Mapping(target = "rol", ignore = true)
    void updateFromDTO(UsuarioDTO dto, @MappingTarget Usuario usuario);
}
