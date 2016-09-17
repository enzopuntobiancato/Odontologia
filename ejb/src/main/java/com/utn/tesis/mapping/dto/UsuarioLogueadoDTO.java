package com.utn.tesis.mapping.dto;

import com.google.common.collect.ImmutableMap;
import com.utn.tesis.model.Rol;
import com.utn.tesis.model.RolEnum;

import java.util.List;
import java.util.Map;

public class UsuarioLogueadoDTO extends BaseDTO {
    private static final long serialVersionUID = -998155527497569957L;

    public static final String PARAM_AUTH_ID = "auth-id";
    public static final String PARAM_AUTH_TOKEN = "auth-token";

    public static final Map<String, Class<? extends PersonaDTO>> rolToPerson = ImmutableMap.<String, Class<? extends PersonaDTO>>builder()
            .put(RolEnum.ADMINISTRADOR.getKey(), AdministradorDTO.class)
            .put(RolEnum.ADMINISTRADOR_ACADEMICO.getKey(), AdministradorAcademicoDTO.class)
            .put(RolEnum.ALUMNO.getKey(), AlumnoDTO.class)
            .put(RolEnum.AUTORIDAD.getKey(), AutoridadDTO.class)
            .put(RolEnum.PROFESOR.getKey(), ProfesorDTO.class)
            .put(RolEnum.RESPONSABLE_RECEPCION_PACIENTES.getKey(), ResponsableRecepcionDTO.class)
            .build();

    private Long id;
    private String nombreUsuario;
    private String authToken;
    private EnumDTO rol;
    private List<PrivilegioDTO> permisos;
    private boolean firstLogin;
    private Long imagenId;

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
        return RolEnum.ADMINISTRADOR.getKey().equals(rol.getKey());
    }

    public boolean isAdminAcademino() {
        return RolEnum.ADMINISTRADOR_ACADEMICO.getKey().equals(rol.getKey());
    }

    public boolean isAlumno() {
        return RolEnum.ALUMNO.getKey().equals(rol.getKey());
    }

    public boolean isAutoridad() {
        return RolEnum.AUTORIDAD.getKey().equals(rol.getKey());
    }

    public boolean isProfesor() {
        return RolEnum.PROFESOR.getKey().equals(rol.getKey());
    }

    public boolean isResponsable() {
        return RolEnum.RESPONSABLE_RECEPCION_PACIENTES.getKey().equals(rol.getKey());
    }

    public Long getImagenId() {
        return imagenId;
    }

    public void setImagenId(Long imagenId) {
        this.imagenId = imagenId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
