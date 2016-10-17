package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.AsignacionPacienteDTO;
import com.utn.tesis.model.AsignacionPaciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 10/08/16
 * Time: 23:48
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi", uses = {TrabajoPracticoMapper.class, PacienteMapper.class,
        DocumentoMapper.class,PersonaMapper.class, MovimientoAsignacionMapper.class,
        DiagnosticoMapper.class, CatedraMapper.class})
public interface AsignacionPacienteMapper {
    @Mappings({
            @Mapping(source = "paciente.id", target = "idPaciente"),
            @Mapping(source = "paciente.apellido", target = "apellidoPaciente"),
            @Mapping(source = "paciente.nombre", target = "nombrePaciente"),
            @Mapping(source = "paciente.documento.tipoDocumento", target = "tipoDocumentoPaciente"),
            @Mapping(source = "paciente.documento.numero", target = "numeroDocumentoPaciente"),
            @Mapping(source = "paciente.email", target = "email"),
            @Mapping(source = "paciente.celular", target = "celular"),
            @Mapping(source = "paciente.telefono", target = "telefono")
    }
    )
    AsignacionPacienteDTO toDTO(AsignacionPaciente entity);

    AsignacionPaciente fromDTO(AsignacionPacienteDTO dto);

    List<AsignacionPacienteDTO> toDTOList(List<AsignacionPaciente> entities);

    void updateFromDTO(AsignacionPacienteDTO dto, @MappingTarget AsignacionPaciente entity);
}
