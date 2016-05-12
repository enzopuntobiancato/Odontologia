package com.utn.tesis.mapping.dto;

public class UsuarioDTO extends BaseDTO {
    private static final long serialVersionUID = -4163955333854545006L;

    private String nombreUsuario;
    private String email;
    private RolDTO rol;
    private Long id;
    // Diferente nombre para no mapear la contraseña
    private String currentPassword;
    private String password;
    private boolean changePassword;
    private boolean fromFirstLogin;
    private Long imagenId;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public RolDTO getRol() {
        return rol;
    }

    public void setRol(RolDTO rolDTO) {
        this.rol = rolDTO;
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
}
