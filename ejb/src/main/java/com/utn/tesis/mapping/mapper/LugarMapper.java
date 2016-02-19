package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.BarrioDTO;
import com.utn.tesis.mapping.dto.CiudadDTO;
import com.utn.tesis.mapping.dto.ProvinciaDTO;
import com.utn.tesis.model.Barrio;
import com.utn.tesis.model.Ciudad;
import com.utn.tesis.model.Provincia;
import org.mapstruct.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 18/02/16
 * Time: 22:42
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi")
public interface LugarMapper {

    ProvinciaDTO fromProvincia(Provincia provincia);

    CiudadDTO fromCiudad(Ciudad ciudad);

    BarrioDTO fromBarrio(Barrio barrio);
}
