package com.utn.tesis.service.authentication;

import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.Rol;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.UsuarioService;
import com.utn.tesis.util.Collections;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 02/06/15
 * Time: 22:42
 */
@Stateless
public class AuthService {

    @Inject
    UsuarioService usuarioService;

    public AuthAccessElement login(AuthLoginElement loginElement) {
        Usuario user = usuarioService.findByUserAndPassword(loginElement.getUsername(), loginElement.getPassword());
        if (user != null) {
            Collections.reload(user, 2);
            if (user.getRoles().size() == 1) {
                loginElement.setRol(user.getRoles().get(0));
                return selectRol(loginElement, false);
            } else {
                return new AuthAccessElement(user.getRoles());
            }
        }
        return null;
    }

    public AuthAccessElement selectRol(AuthLoginElement loginElement, boolean hasMoreRoles) {
        Usuario user = usuarioService.findByUserAndPassword(loginElement.getUsername(), loginElement.getPassword());
        if (user != null) {
            Collections.reload(user, 2);
            user.setAuthToken(UUID.randomUUID().toString());
            user.setAuthRol(loginElement.getRol().getNombre());
            try {
                usuarioService.update(user);
            } catch (SAPOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return new AuthAccessElement(loginElement.getUsername(), user.getAuthToken(), loginElement.getRol().toString(), loginElement.getRol().getPrivilegios(), hasMoreRoles);
        }
        return null;
    }

    public boolean isAuthorized(String authId, String authToken, Set<String> rolesAllowed) {
        Usuario user = usuarioService.findByUsernameAndAuthToken(authId, authToken);

        if (user != null) {
            if (user.getAuthRol().equals(Rol.ADMIN)) {
                return true;
            } else {
                return rolesAllowed.contains(user.getAuthRol());
            }
        } else {
            return false;
        }
    }

}
