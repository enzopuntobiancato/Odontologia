package com.utn.tesis.model;

import com.utn.tesis.annotation.JsonMap;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.util.RegexUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 02/06/15
 * Time: 22:50
 */
@Entity
public class Usuario extends Bajeable {

    @NotNull
    @Size (max = 50)
    private String nombreUsuario;
    @NotNull
    @Size (max = 50)
    private String contrasenia;
    @NotNull
    private String email;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_x_rol",
            joinColumns = {
                    @JoinColumn(name = "usuario_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "rol_id")})
    private List<Rol> roles;
    private String authToken;
    private String authRol;

    @Override
    public void validar() throws SAPOValidationException {
        HashMap<String, String> e = new HashMap<String, String>();

        if (!RegexUtils.validateEmail(email)) {
            e.put("E-mail inválido", "Ingrese un e-mail válido.");
        }

        if(!e.isEmpty()) {
            throw new SAPOValidationException(e);
        }
    }

    @JsonMap(view = JsonMap.Public.class)
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @JsonMap(view = JsonMap.Internal.class)
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @JsonMap(view = JsonMap.Public.class)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonMap(view = JsonMap.Public.class)
    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    @JsonMap(view = JsonMap.Internal.class)
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @JsonMap(view = JsonMap.Internal.class)
    public String getAuthRol() {
        return authRol;
    }

    public void setAuthRol(String authRol) {
        this.authRol = authRol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        if (!super.equals(o)) return false;

        Usuario usuario = (Usuario) o;

        if (authRol != null ? !authRol.equals(usuario.authRol) : usuario.authRol != null) return false;
        if (authToken != null ? !authToken.equals(usuario.authToken) : usuario.authToken != null) return false;
        if (contrasenia != null ? !contrasenia.equals(usuario.contrasenia) : usuario.contrasenia != null) return false;
        if (email != null ? !email.equals(usuario.email) : usuario.email != null) return false;
        if (nombreUsuario != null ? !nombreUsuario.equals(usuario.nombreUsuario) : usuario.nombreUsuario != null)
            return false;
        if (roles != null ? !roles.equals(usuario.roles) : usuario.roles != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nombreUsuario != null ? nombreUsuario.hashCode() : 0);
        result = 31 * result + (contrasenia != null ? contrasenia.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (authToken != null ? authToken.hashCode() : 0);
        result = 31 * result + (authRol != null ? authRol.hashCode() : 0);
        return result;
    }
}
