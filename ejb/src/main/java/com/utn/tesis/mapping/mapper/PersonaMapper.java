package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.model.*;
import com.utn.tesis.util.Collections;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 16:17
 */
@Mapper(componentModel = "cdi", config = PersonaMapperConfig.class)
public abstract class PersonaMapper {

    @Mapping(target = "nombreRol", ignore = true)
    public abstract AlumnoDTO alumnoToDTO(Alumno source);

    public abstract AutoridadDTO autoridadToDTO(Autoridad source);

    public abstract ProfesorDTO profesorToDTO(Profesor source);

    public abstract ResponsableRecepcionDTO responsableToDTO(ResponsableRecepcion source);

    public abstract AdministradorAcademicoDTO adminAcademicoToDTO(AdministradorAcademico source);

    public abstract AdministradorDTO adminToDTO(Administrador source);

    public abstract Alumno alumnoFromDTO(AlumnoDTO source);

    public abstract Autoridad autoridadFromDTO(AutoridadDTO source);

    public abstract Profesor profesorFromDTO(ProfesorDTO source);

    public abstract ResponsableRecepcion responsableFromDTO(ResponsableRecepcionDTO source);

    public abstract AdministradorAcademico adminAcademicoFromDTO(AdministradorAcademicoDTO source);

    public abstract Administrador adminFromDTO(AdministradorDTO source);

    public abstract void updateAlumnoFromDTO(AlumnoDTO source, @MappingTarget Alumno target);

    public abstract void updateAutoridadFromDTO(AutoridadDTO source, @MappingTarget Autoridad target);

    public abstract void updateProfesorFromDTO(ProfesorDTO source, @MappingTarget Profesor target);

    public abstract void updateResponsabeFromDTO(ResponsableRecepcionDTO source, @MappingTarget ResponsableRecepcion target);

    public abstract void updateAdminAcademicoFromDTO(AdministradorAcademicoDTO source, @MappingTarget AdministradorAcademico target);

    public abstract void updateAdminFromDTO(AdministradorDTO source, @MappingTarget Administrador target);

    public abstract List<AlumnoDTO> toAlumnoDTOList(List<Alumno> alumnos);

    public abstract List<ProfesorDTO> toProfesorDTOList(List<Profesor> profesores);

    public PersonaDTO toDTO(Persona source) {
        if (source instanceof Alumno) {
            return this.alumnoToDTO((Alumno) source);
        } else if (source instanceof Autoridad) {
            return this.autoridadToDTO((Autoridad) source);
        } else if (source instanceof ResponsableRecepcion) {
            return this.responsableToDTO((ResponsableRecepcion) source);
        }else if (source instanceof Profesor) {
            return this.profesorToDTO((Profesor) source);
        }else if (source instanceof AdministradorAcademico) {
            return this.adminAcademicoToDTO((AdministradorAcademico) source);
        }else if (source instanceof Administrador) {
            return this.adminToDTO((Administrador) source);
        }
        return null;
    }

    public Persona fromDTO(PersonaDTO source){
       if (source instanceof AlumnoDTO){
           return this.alumnoFromDTO((AlumnoDTO) source);
       }else if (source instanceof AutoridadDTO){
           return this.autoridadFromDTO((AutoridadDTO) source);
       } else if(source instanceof ResponsableRecepcionDTO){
           return this.responsableFromDTO((ResponsableRecepcionDTO) source);
        }else if(source instanceof ProfesorDTO){
           return this.profesorFromDTO((ProfesorDTO) source);
       }else if(source instanceof AdministradorAcademicoDTO){
           return this.adminAcademicoFromDTO((AdministradorAcademicoDTO) source);
       }else if(source instanceof AdministradorDTO){
           return this.adminFromDTO((AdministradorDTO)source);
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

    public List<? extends PersonaDTO> toDTOList(List<? extends Persona> source) {
        if(source == null || source.isEmpty()){
            return null;
        }
        Persona p = source.get(0);
        if (p instanceof Alumno){
            List<Alumno> b = (List<Alumno>) (List<?>) source;
            return this.toAlumnoDTOList(b);
        } else if(p instanceof Profesor){
            List<Profesor> b = (List<Profesor>) (List<?>) source;
            return toProfesorDTOList(b);
        }

        return null;
    }


}
