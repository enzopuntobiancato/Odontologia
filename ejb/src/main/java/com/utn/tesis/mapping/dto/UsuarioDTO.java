package com.utn.tesis.mapping.dto;

import java.util.Calendar;
import java.util.List;

public class UsuarioDTO extends BaseDTO {
    private static final long serialVersionUID = -4163955333854545006L;

    private String nombreUsuario;
    private String email;
    private List<RolUsuarioDTO> roles;
    // Diferente nombre para no mapear la contraseña
    private String currentPassword;
    private String password;
    private boolean changePassword;
    private boolean fromFirstLogin;
    private Long imagenId;
    private Calendar ultimaConexion;
    private Calendar fechaBaja;
    private String motivoBaja;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public Long getImagenId() {
        return imagenId;
    }

    public void setImagenId(Long imagenId) {
        this.imagenId = imagenId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public boolean isFromFirstLogin() {
        return fromFirstLogin;
    }

    public void setFromFirstLogin(boolean fromFirstLogin) {
        this.fromFirstLogin = fromFirstLogin;
    }

    public Calendar getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(Calendar ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
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
}
