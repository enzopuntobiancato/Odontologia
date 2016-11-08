package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.RolUsuarioDTO;
import com.utn.tesis.model.RolUsuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi", uses = {PersonaMapper.class, RollMapper.class})
public interface RolUsuarioMapperWithPerson {
    RolUsuarioDTO toDTO(RolUsuario source);
    List<RolUsuarioDTO> toDTOList(List<RolUsuario> source);
}
