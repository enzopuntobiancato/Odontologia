package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.MovimientoAsignacionDTO;
import com.utn.tesis.model.MovimientoAsignacionPaciente;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 10/09/16
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi", uses = {EnumMapper.class, UsuarioMapper.class})
public interface MovimientoAsignacionMapper {
    MovimientoAsignacionDTO toDTO(MovimientoAsignacionPaciente entity);

    MovimientoAsignacionPaciente fromDTO(MovimientoAsignacionDTO dto);

    List<MovimientoAsignacionPaciente> toDTOList(List<MovimientoAsignacionDTO> dtos);
}
