package com.utn.tesis.mapping.dto;

import com.utn.tesis.model.RolEnum;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 17:18
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "nombreRol")
@JsonSubTypes({@JsonSubTypes.Type(value = AlumnoDTO.class, name=RolEnum.Constants.ALUMNO),
        @JsonSubTypes.Type(value = ProfesorDTO.class, name=RolEnum.Constants.PROFESOR),
        @JsonSubTypes.Type(value = ResponsableRecepcionDTO.class, name=RolEnum.Constants.RESPONSABLE_RECEPCION_PACIENTES),
        @JsonSubTypes.Type(value = AutoridadDTO.class, name=RolEnum.Constants.AUTORIDAD),
        @JsonSubTypes.Type(value = AdministradorAcademicoDTO.class, name=RolEnum.Constants.ADMINISTRADOR_ACADEMICO),
        @JsonSubTypes.Type(value = AdministradorDTO.class, name=RolEnum.Constants.ADMINISTRADOR)})
public abstract class PersonaDTO extends BaseDTO {

    private Long id;
    private String apellido;
    private String nombre;
    private DocumentoDTO documento;
    private Calendar fechaNacimiento;
    private EnumDTO sexo;
    private UsuarioDTO usuario;
    private String nombreRol;

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DocumentoDTO getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoDTO documento) {
        this.documento = documento;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public EnumDTO getSexo() {
        return sexo;
    }

    public void setSexo(EnumDTO sexo) {
        this.sexo = sexo;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
