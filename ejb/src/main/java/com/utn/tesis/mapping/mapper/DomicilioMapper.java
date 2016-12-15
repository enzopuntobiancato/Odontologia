package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.BarrioDTO;
import com.utn.tesis.mapping.dto.DomicilioDTO;
import com.utn.tesis.model.Barrio;
import com.utn.tesis.model.Domicilio;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 20/02/16
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi", uses = {BarrioMapper.class})
public interface DomicilioMapper {

    @Mapping(source = "numero", target = "numeroCalle")
    DomicilioDTO toDTO(Domicilio domicilio);

    @InheritInverseConfiguration
    Domicilio fromDTO(DomicilioDTO domicilioDTO);

    void updateDomicilioFromDTO(DomicilioDTO domicilioDTO, @MappingTarget Domicilio domicilio);

   /* DomicilioDTO toDomicilioDTO(Domicilio domicilio);

    Domicilio fromDomicilioDTO(DomicilioDTO domicilioDTO);

    @Mapping(target = "ciudad", ignore = true)
    BarrioDTO toBarrioDTO(Barrio barrio);

    @Mapping(source = "ciudad.id",target = "ciudad")
    Barrio fromBarrioDTO(BarrioDTO barrioDTO);*/
}
