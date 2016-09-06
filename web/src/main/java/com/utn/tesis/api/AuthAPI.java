package com.utn.tesis.api;

import com.utn.tesis.mapping.dto.LoginDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.service.authentication.AuthService;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 02/06/15
 * Time: 22:24
 */
@Path("/auth")
@RequestScoped
public class AuthAPI {

    @Inject
    private AuthService authService;

    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @PermitAll
    public UsuarioLogueadoDTO login(@Context HttpServletRequest request, LoginDTO loginElement) {
        UsuarioLogueadoDTO usuarioLogueado = authService.login(loginElement);

        if (usuarioLogueado != null && usuarioLogueado.getNombreUsuario() != null && usuarioLogueado.getAuthToken() != null) {
            request.getSession(true).setMaxInactiveInterval(15);
            request.getSession().setAttribute(UsuarioLogueadoDTO.PARAM_AUTH_ID, usuarioLogueado.getNombreUsuario());
            request.getSession().setAttribute(UsuarioLogueadoDTO.PARAM_AUTH_TOKEN, usuarioLogueado.getAuthToken());
        }
        return usuarioLogueado;
    }
}
