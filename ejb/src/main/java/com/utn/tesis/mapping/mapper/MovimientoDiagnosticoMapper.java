package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.MovimientoDiagnosticoDTO;
import com.utn.tesis.model.MovimientoDiagnostico;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "cdi", uses = { AtencionMapper.class, EnumMapper.class },
nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface MovimientoDiagnosticoMapper {
    MovimientoDiagnosticoDTO toDTO(MovimientoDiagnostico source);
    @Mapping(target = "atencion", ignore = true)
    MovimientoDiagnostico fromDTO(MovimientoDiagnosticoDTO source);
    @InheritConfiguration
    void updateFromDTO(MovimientoDiagnosticoDTO source, @MappingTarget MovimientoDiagnostico target);
    List<MovimientoDiagnosticoDTO> toDTOList(List<MovimientoDiagnostico> sourceList);
    @InheritConfiguration
    List<MovimientoDiagnostico> fromDTOList(List<MovimientoDiagnosticoDTO> sourceList);
}
