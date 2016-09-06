package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.DiagnosticoDTO;
import com.utn.tesis.model.Diagnostico;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi", uses = {PracticaOdontologicaMapper.class, MovimientoDiagnosticoMapper.class})
public interface DiagnosticoMapper {
    Diagnostico fromDTO(DiagnosticoDTO source);

    DiagnosticoDTO toDTO(Diagnostico source);

    List<DiagnosticoDTO> toDTOList(List<Diagnostico> sourceList);
}
