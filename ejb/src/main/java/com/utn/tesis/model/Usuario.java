package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.util.RegexUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario extends Bajeable {
    private static final long serialVersionUID = 4712822060350795922L;

    @NotNull(message = "El nombre de usuario no puede ser nulo.")
    @Size(max = 50, message = "El nombre de usuario debe tener entre 1 y 50 caracteres.")
    @Column(nullable = false, length = 50, name = "nombre_usuario")
    private String nombreUsuario;

    @NotNull(message = "La contraseña de usuario no puede ser nula.")
    @Size(max = 50, message = "La contraseña de usuario debe tener entre 1 y 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String contrasenia;

    @NotNull(message = "El email del usuario no puede ser nulo.")
    @Size(max = 70, message = "El email del usuario debe tener entre 1 y 70 caracteres.")
    @Column(nullable = false, length = 70)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    @NotEmpty(message = "El usuario debe tener un rol")
    private List<RolUsuario> roles = new ArrayList<RolUsuario>();

    @ManyToOne
    @JoinColumn(name = "archivo_imagen_id")
    private Archivo imagen;

    @Temporal(TemporalType.DATE)
    @Column(name = "ultima_conexion")
    private Calendar ultimaConexion;

    // UUID que se genera cdo un usuario inicia sesión
    @Column(name = "auth_token")
    private String authToken;

    @Override
    public void validar() throws SAPOValidationException {
        HashMap<String, String> e = new HashMap<String, String>();

        if (!RegexUtils.validateEmail(email)) {
            e.put("E-mail inválido", "Ingrese un e-mail válido.");
        }

        if (roles == null || roles.isEmpty()) {
            e.put("Rol Null or Empty", "El usuario debe tener al menos un rol");
        }

        if (!e.isEmpty()) {
            throw new SAPOValidationException(e);
        }
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Archivo getImagen() {
        return imagen;
    }

    public void setImagen(Archivo imagen) {
        this.imagen = imagen;
    }

    public Calendar getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(Calendar ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }

    public List<RolUsuario> getRoles() {
        return roles;
    }

    public void setRoles(List<RolUsuario> roles) {
        this.roles = roles;
    }

    public Persona retrieveFirstPerson() {
        return roles.get(0).getPersona();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        if (!super.equals(o)) return false;

        Usuario usuario = (Usuario) o;

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
        return result;
    }
}
