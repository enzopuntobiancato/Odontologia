package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.DiaHorarioDTO;
import com.utn.tesis.model.DiaHorario;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 7/02/16
 * Time: 18:30
 */
@Mapper (componentModel = "cdi", uses = EnumMapper.class)
public interface DiaHorarioMapper {
    DiaHorarioDTO toDTO(DiaHorario source);
    DiaHorario fromDTO(DiaHorarioDTO source);
    List<DiaHorario> fromDTOList(List<DiaHorarioDTO> list);
    List<DiaHorarioDTO> toDTOList(List<DiaHorario> list);
}
