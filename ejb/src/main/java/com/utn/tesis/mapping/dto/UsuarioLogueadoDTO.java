package com.utn.tesis.mapping.dto;

import com.utn.tesis.model.RolEnum;
import com.utn.tesis.model.Usuario;

import java.util.List;

public class UsuarioLogueadoDTO extends BaseDTO {
    private static final long serialVersionUID = -998155527497569957L;

    public static final String PARAM_AUTH_ID = "auth-id";
    public static final String PARAM_AUTH_TOKEN = "auth-token";

    private String nombreUsuario;
    private String authToken;
    private EnumDTO rol;
    private boolean firstLogin;
    private boolean hasMultipleRoles;
    private Long imagenId;

    public static UsuarioLogueadoDTO valueOf(Usuario usuario) {
        UsuarioLogueadoDTO usuarioLogueado = new UsuarioLogueadoDTO();
        usuarioLogueado.setId(usuario.getId());
        usuarioLogueado.setAuthToken(usuario.getAuthToken());
        usuarioLogueado.setVersion(usuario.getVersion());
        usuarioLogueado.setFirstLogin(usuario.getUltimaConexion() == null);
        usuarioLogueado.setHasMultipleRoles(usuario.getRoles().size() > 1);
        usuarioLogueado.setImagenId(usuario.getImagen() != null ? usuario.getImagen().getId() : null);
        usuarioLogueado.setNombreUsuario(usuario.getNombreUsuario());
        return usuarioLogueado;
    }

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

    public EnumDTO getRol() {
        return rol;
    }

    public void setRol(EnumDTO rol) {
        this.rol = rol;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public boolean isAdministrador() {
        if (rol != null) {
            return RolEnum.ADMINISTRADOR.getKey().equals(rol.getKey());
        }
        return false;
    }

    public boolean isAdminAcademino() {
        if (rol != null) {
            return RolEnum.ADMINISTRADOR_ACADEMICO.getKey().equals(rol.getKey());

        }
        return false;
    }

    public boolean isAlumno() {
        if (rol != null) {
            return RolEnum.ALUMNO.getKey().equals(rol.getKey());

        }
        return false;
    }

    public boolean isAutoridad() {
        if (rol != null) {
            return RolEnum.AUTORIDAD.getKey().equals(rol.getKey());

        }
        return false;
    }

    public boolean isProfesor() {
        if (rol != null) {
            return RolEnum.PROFESOR.getKey().equals(rol.getKey());
        }
        return false;

    }

    public boolean isResponsable() {
        if (rol != null) {
            return RolEnum.RESPONSABLE_RECEPCION_PACIENTES.getKey().equals(rol.getKey());
        }
        return false;
    }

    public Long getImagenId() {
        return imagenId;
    }

    public void setImagenId(Long imagenId) {
        this.imagenId = imagenId;
    }

    public boolean isHasMultipleRoles() {
        return hasMultipleRoles;
    }

    public void setHasMultipleRoles(boolean hasMultipleRoles) {
        this.hasMultipleRoles = hasMultipleRoles;
    }
}
