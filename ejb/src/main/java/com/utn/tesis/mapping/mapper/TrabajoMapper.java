package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.TrabajoDTO;
import com.utn.tesis.model.Trabajo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 6/03/16
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi")
public interface TrabajoMapper {
    TrabajoDTO toDTO(Trabajo trabajo);
    Trabajo fromDTO(TrabajoDTO trabajoDTO);
    List<TrabajoDTO> toDTOList(List<Trabajo> trabajos);
}
