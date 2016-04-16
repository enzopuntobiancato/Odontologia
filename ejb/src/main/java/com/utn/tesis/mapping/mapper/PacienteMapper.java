package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.PacienteConsultaDTO;
import com.utn.tesis.mapping.dto.PacienteDTO;
import com.utn.tesis.model.Paciente;
import org.mapstruct.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 20/02/16
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi", uses = {EnumMapper.class,
        TrabajoMapper.class,ObraSocialMapper.class, CiudadMapper.class,
        DocumentoMapper.class, DomicilioMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface PacienteMapper {


    Paciente fromDTO(PacienteDTO pacienteDTO);

    PacienteDTO toDTO(Paciente paciente);

    void updataFromDTO(PacienteDTO pacienteDTO, @MappingTarget Paciente paciente);

    List<PacienteDTO> toDTOList(List<Paciente> pacientes);


}
