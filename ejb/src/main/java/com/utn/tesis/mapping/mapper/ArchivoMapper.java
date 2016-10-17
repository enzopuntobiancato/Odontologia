package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.ArchivoDTO;
import com.utn.tesis.model.Archivo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 1/02/16
 * Time: 16:47
 */
@Mapper(componentModel = "cdi")
public interface ArchivoMapper {

    ArchivoDTO toDTO(Archivo source);
    List<ArchivoDTO> toDTOList(List<Archivo> sourceList);

    void updateFromDTO(ArchivoDTO source, @MappingTarget Archivo target);
}
