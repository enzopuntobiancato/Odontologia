package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.PersonaDTO;
import com.utn.tesis.model.Persona;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 17:16
 */
@MapperConfig(componentModel = "cdi", uses = {DocumentoMapper.class, EnumMapper.class, UsuarioMapper.class},
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface PersonaMapperConfig {

    PersonaDTO toDTO(Persona source);

    void updateFromDTO(PersonaDTO source, @MappingTarget Persona persona);
}
