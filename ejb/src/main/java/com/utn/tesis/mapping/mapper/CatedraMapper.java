package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.CatedraConsultaDTO;
import com.utn.tesis.mapping.dto.CatedraDTO;
import com.utn.tesis.model.Catedra;
import org.mapstruct.*;

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
    List<CatedraDTO> toDTOList(List<Catedra> source);
    @Mapping(target= "materia", ignore = true)
    Catedra fromDTO(CatedraDTO source);
    @Mapping(target= "materia", ignore = true)
    void updateFromDTO(CatedraDTO source, @MappingTarget Catedra target);
    @Mapping(source = "materia.nombre", target = "materia")
    CatedraConsultaDTO toConsultaDTO(Catedra source);
    @Mapping(target = "materia", ignore = true)
    Catedra fromConsultaDTO(CatedraConsultaDTO source);
    @InheritConfiguration
    List<CatedraConsultaDTO> toConsultaDTOList(List<Catedra> sourceList);
}
