package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.PacienteDTO;
import com.utn.tesis.model.Paciente;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "cdi", uses = {
        EnumMapper.class,
        TrabajoMapper.class,
        ObraSocialMapper.class,
        CiudadMapper.class,
        DocumentoMapper.class,
        DomicilioMapper.class,
        BarrioMapper.class
}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface PacienteMapper {

    Paciente fromDTO(PacienteDTO pacienteDTO);

    @Mappings({
            @Mapping(source = "paciente.historiaClinica.id", target = "historiaClinicaId"),
            @Mapping(source = "paciente.imagen.id", target = "imagenId")
    })
    PacienteDTO toDTO(Paciente paciente);

    void updataFromDTO(PacienteDTO pacienteDTO, @MappingTarget Paciente paciente);

    List<PacienteDTO> toDTOList(List<Paciente> pacientes);


}
