package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.FuncionalidadDTO;
import com.utn.tesis.model.Funcionalidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 13:55
 */
@Mapper(componentModel = "cdi")
public interface FuncionalidadMapper {

    @Mapping(source = "grupoFuncionalidad.nombre", target = "grupoFuncionalidad")
    FuncionalidadDTO toDTO(Funcionalidad funcionalidad);

    List<FuncionalidadDTO> toDTOList(List<Funcionalidad> sourceList);
}
