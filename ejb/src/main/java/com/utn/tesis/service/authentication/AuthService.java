package com.utn.tesis.service.authentication;

import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.UsuarioService;

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
             user.setAuthToken(UUID.randomUUID().toString());
            try {
                usuarioService.update(user);
            } catch (SAPOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return new AuthAccessElement(loginElement.getUsername(), user.getAuthToken(), user.getRol().toString());
        }
        return null;
    }

    public boolean isAuthorized(String authId, String authToken, Set<String> rolesAllowed) {
        Usuario user = usuarioService.findByUsernameAndAuthToken(authId, authToken);
        if (user != null) {
            return rolesAllowed.contains(user.getRol().toString());
        } else {
            return false;
        }
    }

}
