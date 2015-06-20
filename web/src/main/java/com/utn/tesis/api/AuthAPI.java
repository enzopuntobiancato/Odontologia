package com.utn.tesis.api;

import com.utn.tesis.service.authentication.AuthAccessElement;
import com.utn.tesis.service.authentication.AuthLoginElement;
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
    AuthService authService;

    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @PermitAll
    public AuthAccessElement login(@Context HttpServletRequest request, AuthLoginElement loginElement) {
        AuthAccessElement accessElement = authService.login(loginElement);
        if (accessElement != null) {
            request.getSession().setMaxInactiveInterval(15);
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_ID, accessElement.getAuthId());
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_TOKEN, accessElement.getAuthToken());
        }
           return accessElement;
    }
}
