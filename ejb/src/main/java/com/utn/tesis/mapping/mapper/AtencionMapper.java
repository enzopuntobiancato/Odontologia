package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.AtencionDTO;
import com.utn.tesis.mapping.dto.AtencionLightDTO;
import com.utn.tesis.model.Atencion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi", uses = AsignacionPacienteMapper.class)
public interface AtencionMapper {
    AtencionLightDTO toLightDTO(Atencion source);
    Atencion fromDTO(AtencionDTO source);
    AtencionDTO toDTO(Atencion source);
    List<AtencionDTO> toDTOList(List<Atencion> sourceList);
}
