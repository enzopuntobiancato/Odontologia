package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.RolUsuarioDTO;
import com.utn.tesis.model.RolUsuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "cdi", uses = {RollMapper.class})
public interface RolUsuarioMapper {
    @InheritInverseConfiguration
    RolUsuario fromDTO(RolUsuarioDTO source);
    @Mappings( {
            @Mapping(target = "persona", ignore = true),
            @Mapping(target = "personaId", source = "persona.id")
    })
    RolUsuarioDTO toDTO(RolUsuario source);
    @InheritConfiguration
    List<RolUsuarioDTO> toDTOList(List<RolUsuario> sourceList);
}
