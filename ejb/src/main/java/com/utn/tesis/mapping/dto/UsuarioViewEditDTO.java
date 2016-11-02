package com.utn.tesis.mapping.dto;

import com.utn.tesis.model.RolEnum;

import java.util.Calendar;
import java.util.List;

public class UsuarioViewEditDTO extends BaseDTO {
    private static final long serialVersionUID = 185918983211890755L;

    private String nombreUsuario;
    private String email;
    private List<RolUsuarioDTO> roles;
    private Calendar ultimaConexion;
    private Long imagenId;
    private Calendar fechaBaja;
    private String motivoBaja;

    private String apellido;
    private String nombre;
    private DocumentoDTO documento;
    private EnumDTO sexo;
    private Calendar fechaNacimiento;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RolUsuarioDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RolUsuarioDTO> roles) {
        this.roles = roles;
    }

    public Calendar getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(Calendar ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }

    public Long getImagenId() {
        return imagenId;
    }

    public void setImagenId(Long imagenId) {
        this.imagenId = imagenId;
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

    public EnumDTO getSexo() {
        return sexo;
    }

    public void setSexo(EnumDTO sexo) {
        this.sexo = sexo;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Calendar getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Calendar fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    private boolean containsRole(RolEnum rol) {
        boolean hasRole = false;
        for (RolUsuarioDTO rolUsuario : roles) {
            if (rolUsuario.getRol().getNombre().getKey().equals(rol.getKey())) {
                hasRole = true;
                break;
            }
        }
        return hasRole;
    }

    public boolean isAdministrador() {
        return containsRole(RolEnum.ADMINISTRADOR);
    }

    public boolean isAdminAcademino() {
        return containsRole(RolEnum.ADMINISTRADOR_ACADEMICO);
    }

    public boolean isAlumno() {
        return containsRole(RolEnum.ALUMNO);
    }

    public boolean isAutoridad() {
        return containsRole(RolEnum.AUTORIDAD);
    }

    public boolean isProfesor() {
        return containsRole(RolEnum.PROFESOR);
    }

    public boolean isResponsable() {
        return containsRole(RolEnum.RESPONSABLE_RECEPCION_PACIENTES);
    }

    private PersonaDTO retrievePersonByRole(RolEnum rol) {
        PersonaDTO persona = null;
        for (RolUsuarioDTO rolUsuario : roles) {
            if (rolUsuario.getRol().getNombre().getKey().equals(rol.getKey())) {
                persona = rolUsuario.getPersona();
                break;
            }
        }
        return persona;
    }

    public PersonaDTO getAdministradorDTO() {
        return retrievePersonByRole(RolEnum.ADMINISTRADOR);
    }

    public PersonaDTO getAdminAcademicoDTO() {
        return retrievePersonByRole(RolEnum.ADMINISTRADOR_ACADEMICO);
    }

    public PersonaDTO getAlumnoDTO() {
        return retrievePersonByRole(RolEnum.ALUMNO);
    }

    public PersonaDTO getAutoridadDTO() {
        return retrievePersonByRole(RolEnum.AUTORIDAD);
    }

    public PersonaDTO getProfesorDTO() {
        return retrievePersonByRole(RolEnum.PROFESOR);
    }

    public PersonaDTO getResponsableDTO() {
        return retrievePersonByRole(RolEnum.RESPONSABLE_RECEPCION_PACIENTES);
    }
}
