package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.RolDTO;
import com.utn.tesis.model.Rol;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 4/02/16
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi", uses = EnumMapper.class)
public interface RollMapper {

    Rol fromRolDTO(RolDTO rolDTO);
    RolDTO toRolDTO(Rol rol);
    List<RolDTO> toDTOList(List<Rol> source);
}
