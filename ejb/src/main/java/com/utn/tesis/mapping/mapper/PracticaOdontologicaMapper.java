package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.PracticaOdontologicaDTO;
import com.utn.tesis.model.PracticaOdontologica;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 8/02/16
 * Time: 15:01
 */
@Mapper (componentModel = "cdi", uses = {GrupoPracticaOdontologicaMapper.class})
public interface PracticaOdontologicaMapper {
    PracticaOdontologicaDTO toDTO(PracticaOdontologica source);
    PracticaOdontologica fromDTO(PracticaOdontologicaDTO source);
    void updateFromDTO(PracticaOdontologicaDTO source, @MappingTarget PracticaOdontologica target);
    List<PracticaOdontologicaDTO> toDTOList(List<PracticaOdontologica> sourceList);
}
