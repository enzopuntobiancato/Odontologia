package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.TrabajoPracticoDTO;
import com.utn.tesis.model.TrabajoPractico;
import org.mapstruct.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 12/02/16
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi", uses = {PracticaOdontologicaMapper.class})
public interface TrabajoPracticoMapper {

    TrabajoPracticoDTO toDTO(TrabajoPractico trabajoPractico);

    TrabajoPractico fromDTO(TrabajoPracticoDTO trabajoPracticoDTO);
}
