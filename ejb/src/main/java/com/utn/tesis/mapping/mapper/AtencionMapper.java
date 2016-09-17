package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.AtencionLightDTO;
import com.utn.tesis.model.Atencion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface AtencionMapper {
    AtencionLightDTO toLightDTO(Atencion source);
}
