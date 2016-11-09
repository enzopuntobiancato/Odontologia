package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.RolDTO;
import com.utn.tesis.mapping.dto.RolEditDTO;
import com.utn.tesis.model.Rol;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi", uses = {EnumMapper.class, PrivilegioMapper.class})
public interface RollMapper {

    Rol fromRolDTO(RolDTO rolDTO);
    RolDTO toRolDTO(Rol rol);
    List<RolDTO> toDTOList(List<Rol> source);
    RolEditDTO toRolEditDTO(Rol source);
    List<RolEditDTO> toRolEditDTOList(List<Rol> sourceList);
}
