package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.HistoriaClinicaDTO;
import com.utn.tesis.model.HistoriaClinica;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 1/05/16
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi")
public interface HistoriaClinicaMapper {

    HistoriaClinicaDTO toDTO(HistoriaClinica entity);
    HistoriaClinica fromDTO(HistoriaClinicaDTO dto);
    List<HistoriaClinicaDTO> toDTOList(List<HistoriaClinica> entities);
    void updateFromDTO(HistoriaClinicaDTO source, @MappingTarget HistoriaClinica target);

}
