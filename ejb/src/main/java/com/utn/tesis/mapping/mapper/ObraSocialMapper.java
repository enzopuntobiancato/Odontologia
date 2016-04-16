package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.ObraSocialDTO;
import com.utn.tesis.model.ObraSocial;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 6/03/16
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi")
public interface ObraSocialMapper {
    ObraSocialDTO toDTO(ObraSocial obraSocial);
    ObraSocial fromDTO(ObraSocialDTO obraSocialDTO);
    List<ObraSocialDTO> toDTOList(List<ObraSocial> obrasSociales);

}
