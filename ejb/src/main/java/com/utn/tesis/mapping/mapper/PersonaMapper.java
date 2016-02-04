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
public abstract class PersonaMapper {
    public abstract AlumnoDTO alumnoToDTO(Alumno source);

    public abstract AutoridadDTO autoridadToDTO(Autoridad source);

    public abstract ProfesorDTO profesorToDTO(Profesor source);

    public abstract ResponsableRecepcionDTO responsableToDTO(ResponsableRecepcion source);

    public abstract AdministradorAcademicoDTO adminAcademicoToDTO(AdministradorAcademico source);

    public abstract AdministradorDTO adminToDTO(Administrador source);

    public abstract void updateAlumnoFromDTO(AlumnoDTO source, @MappingTarget Alumno target);

    public abstract void updateAutoridadFromDTO(AutoridadDTO source, @MappingTarget Autoridad target);

    public abstract void updateProfesorFromDTO(ProfesorDTO source, @MappingTarget Profesor target);

    public abstract void updateResponsabeFromDTO(ResponsableRecepcionDTO source, @MappingTarget ResponsableRecepcion target);

    public abstract void updateAdminAcademicoFromDTO(AdministradorAcademicoDTO source, @MappingTarget AdministradorAcademico target);

    public abstract void updateAdminFromDTO(AdministradorDTO source, @MappingTarget Administrador target);

    public PersonaDTO toDTO(Persona source) {
        if (source instanceof Alumno) {
            return this.alumnoToDTO((Alumno)source);
        } else if (source instanceof Autoridad) {
            return this.autoridadToDTO((Autoridad)source);
        } else if (source instanceof ResponsableRecepcion) {
            return this.responsableToDTO((ResponsableRecepcion)source);
        }else if (source instanceof Profesor) {
            return this.profesorToDTO((Profesor)source);
        }else if (source instanceof AdministradorAcademico) {
            return this.adminAcademicoToDTO((AdministradorAcademico)source);
        }else if (source instanceof Administrador) {
            return this.adminToDTO((Administrador)source);
        }
        return null;
    }

    public void updateFromDTO(PersonaDTO source, Persona target) {
        if (source instanceof AlumnoDTO) {
            this.updateAlumnoFromDTO((AlumnoDTO)source, (Alumno)target);
        } else if (source instanceof AutoridadDTO) {
            this.updateAutoridadFromDTO((AutoridadDTO)source, (Autoridad)target);
        } else if (source instanceof ResponsableRecepcionDTO) {
            this.updateResponsabeFromDTO((ResponsableRecepcionDTO)source, (ResponsableRecepcion)target);
        } else if (source instanceof ProfesorDTO) {
            this.updateProfesorFromDTO((ProfesorDTO)source, (Profesor)target);
        } else if (source instanceof AdministradorAcademicoDTO) {
            this.updateAdminAcademicoFromDTO((AdministradorAcademicoDTO)source, (AdministradorAcademico) target);
        } else if (source instanceof AdministradorDTO) {
            this.updateAdminFromDTO((AdministradorDTO)source, (Administrador) target);
        }
    }
}
