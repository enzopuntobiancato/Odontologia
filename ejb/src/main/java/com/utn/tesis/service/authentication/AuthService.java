package com.utn.tesis.service.authentication;

import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.Rol;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 02/06/15
 * Time: 22:42
 */
@Stateless
@Slf4j
public class AuthService {

    @Inject
    UsuarioService usuarioService;


    public AuthAccessElement login(AuthLoginElement loginElement) {
        AuthAccessElement authAccessElement = null;
        Usuario user = usuarioService.findByUserAndPassword(loginElement.getUsername(), loginElement.getPassword());
        if (user != null) {
            user.setAuthToken(UUID.randomUUID().toString());
            try {
                boolean firstLogin = user.getUltimaConexion() == null;
                user.setUltimaConexion(firstLogin ? null : Calendar.getInstance());
                usuarioService.update(user);
                authAccessElement = new AuthAccessElement(user.getNombreUsuario(), user.getAuthToken(), user.getRol().toString(), user.getRol().getPrivilegios(), firstLogin);
            } catch (SAPOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return authAccessElement;
    }

    public boolean isAuthorized(String authId, String authToken, Set<String> rolesAllowed) {
        Usuario user = usuarioService.findByUsernameAndAuthToken(authId, authToken);

        if (user != null) {
            if (user.getRol().equals(Rol.ADMIN)) {
                return true;
            } else {
                return rolesAllowed.contains(user.getRol());
            }
        } else {
            return false;
        }
    }


}
