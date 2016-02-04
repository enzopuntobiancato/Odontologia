package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 3/02/16
 * Time: 22:29
 */
public class UsuarioDTO extends BaseDTO {
    private static final long serialVersionUID = -8451608534035229702L;

    private Long id;
    private String nombreUsuario;
    private String email;
    // Diferente nombre para no mapear la contraseña
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
