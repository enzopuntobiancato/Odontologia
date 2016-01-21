package com.utn.tesis.mapping.mapper;


import com.utn.tesis.mapping.dto.MateriaDTO;
import com.utn.tesis.model.Materia;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 20/01/16
 * Time: 20:19
 */
@Mapper(componentModel = "cdi")
public interface MateriaMapper {

    MateriaMapper INSTANCE = Mappers.getMapper(MateriaMapper.class);

    //    @Mapping(target = "nivel", expression = "java(materia.getNivel().toString())")
    MateriaDTO toDTO(Materia materia);

    Materia fromDTO(MateriaDTO materiaDTO);

    void updateFromDTO(MateriaDTO materiaDTO, @MappingTarget Materia materia);
}
