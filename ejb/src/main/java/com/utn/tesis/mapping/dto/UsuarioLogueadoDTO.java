package com.utn.tesis.mapping.dto;

import com.utn.tesis.model.Rol;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 02/06/15
 * Time: 22:43
 */
public class UsuarioLogueadoDTO extends BaseDTO {
    private static final long serialVersionUID = -998155527497569957L;

    public static final String PARAM_AUTH_ID = "auth-id";
    public static final String PARAM_AUTH_TOKEN = "auth-token";

    private String nombreUsuario;
    private String authToken;
    private String rol;
    private List<PrivilegioDTO> permisos;
    private boolean firstLogin;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<PrivilegioDTO> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PrivilegioDTO> permisos) {
        this.permisos = permisos;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public boolean isAdministrador() {
        return rol.equalsIgnoreCase(Rol.ADMIN);
    }

    public boolean isAdminAcademino() {
        return rol.equalsIgnoreCase(Rol.ADMIN_ACADEMICO);
    }

    public boolean isAlumno() {
        return rol.equalsIgnoreCase(Rol.ALUMNO);
    }

    public boolean isAutoridad() {
        return rol.equalsIgnoreCase(Rol.AUTORIDAD);
    }

    public boolean isProfesor() {
        return rol.equalsIgnoreCase(Rol.PROFESOR);
    }

    public boolean isResponsable() {
        return rol.equalsIgnoreCase(Rol.RESPONSABLE_RECEPCION_PACIENTES);
    }
}
