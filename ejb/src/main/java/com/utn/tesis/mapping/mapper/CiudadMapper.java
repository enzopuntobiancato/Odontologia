package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.CiudadDTO;
import com.utn.tesis.model.Ciudad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 4/03/16
 * Time: 23:44
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi", uses = ProvinciaMapper.class)
public interface CiudadMapper {

    public CiudadDTO toDTO(Ciudad ciudad);

    public Ciudad fromDTO(CiudadDTO ciudadDTO);

    public List<CiudadDTO> toDTOList(List<Ciudad> ciudades);
}
