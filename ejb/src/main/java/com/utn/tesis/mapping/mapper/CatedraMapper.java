package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.CatedraConsultaDTO;
import com.utn.tesis.mapping.dto.CatedraDTO;
import com.utn.tesis.model.Catedra;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 22/02/16
 * Time: 22:49
 */
@Mapper(componentModel = "cdi", uses = {DiaHorarioMapper.class, MateriaMapper.class, TrabajoPracticoMapper.class})
public interface CatedraMapper {
    CatedraDTO toDTO(Catedra source);
    Catedra fromDTO(CatedraDTO source);
    void updateFromDTO(CatedraDTO source, @MappingTarget Catedra target);
    @Mapping(source = "materia.nombre", target = "materia")
    CatedraConsultaDTO toConsultaDTO(Catedra source);
    @InheritConfiguration
    List<CatedraConsultaDTO>  toConsultaDTOList(List<Catedra> sourceList);
}
