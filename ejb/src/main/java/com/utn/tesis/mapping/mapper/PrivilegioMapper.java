package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.PrivilegioDTO;
import com.utn.tesis.model.Privilegio;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi", uses = FuncionalidadMapper.class)
public interface PrivilegioMapper {
    PrivilegioDTO toPrivilegioDTO(Privilegio privilegio);
    List<PrivilegioDTO> toDTOList(List<Privilegio> source);
}
