package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.GrupoPracticaOdontologicaDTO;
import com.utn.tesis.model.GrupoPracticaOdontologica;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 8/02/16
 * Time: 14:59
 */
@Mapper(componentModel = "cdi")
public interface GrupoPracticaOdontologicaMapper {
    GrupoPracticaOdontologicaDTO toDTO(GrupoPracticaOdontologica source);
    GrupoPracticaOdontologica fromDTO(GrupoPracticaOdontologicaDTO source);
    List<GrupoPracticaOdontologicaDTO> toDTOList(List<GrupoPracticaOdontologica> sourceList);
}
