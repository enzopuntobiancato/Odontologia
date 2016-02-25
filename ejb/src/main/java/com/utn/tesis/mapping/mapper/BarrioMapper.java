package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.BarrioDTO;
import com.utn.tesis.model.Barrio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 4/03/16
 * Time: 23:47
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi", uses = {CiudadMapper.class})
public interface BarrioMapper {

    public BarrioDTO toDTO(Barrio barrio);

    public Barrio fromDTO(BarrioDTO barrioDTO);

    public List<BarrioDTO> toDTOList(List<Barrio> barrios);
}
