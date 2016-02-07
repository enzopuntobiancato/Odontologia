package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 4/02/16
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class UsuarioDTO {
    private String nombreUsuario;
    private String email;
    private RolDTO rol;
    private Long id;
    private String contrasenia;

    public UsuarioDTO() {
    }


    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
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
}
