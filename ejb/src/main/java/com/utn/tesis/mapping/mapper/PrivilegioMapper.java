package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.PrivilegioDTO;
import com.utn.tesis.model.Privilegio;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 13:58
 */
@Mapper(componentModel = "cdi", uses = FuncionalidadMapper.class)
public interface PrivilegioMapper {

    PrivilegioDTO toPrivilegioDTO(Privilegio privilegio);
    List<PrivilegioDTO> toDTOList(List<Privilegio> source);
}
