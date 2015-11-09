package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 16:17
 */
@Mapper(componentModel = "cdi", config = PersonaMapperConfig.class)
public interface PersonaMapper {
    AlumnoDTO alumnoToDTO(Alumno source);

    AutoridadDTO autoridadToDTO(Autoridad source);

    ProfesorDTO profesorToDTO(Profesor source);

    ResponsableRecepcionDTO responsableToDTO(ResponsableRecepcion source);

    AdministradorAcademicoDTO adminAcademicoToDTO(AdministradorAcademico source);

    AdministradorDTO adminToDTO(Administrador source);

    void updateAlumnoFromDTO(AlumnoDTO source, @MappingTarget Alumno target);

    void updateAutoridadFromDTO(AutoridadDTO source, @MappingTarget Autoridad target);

    void updateProfesorFromDTO(ProfesorDTO source, @MappingTarget Profesor target);

    void updateResponsabeFromDTO(ResponsableRecepcionDTO source, @MappingTarget ResponsableRecepcion target);

    void updateAdminAcademicoFromDTO(AdministradorAcademicoDTO source, @MappingTarget AdministradorAcademico target);

    void updateAdminFromDTO(AdministradorDTO source, @MappingTarget Administrador target);
}
