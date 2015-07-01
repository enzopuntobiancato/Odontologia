package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.BaseService;
import com.utn.tesis.service.UsuarioService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 30/06/15
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
@Path("/usuario")
@RequestScoped
public class UsuarioAPI extends BaseAPI<Usuario> {

    @Inject
    UsuarioService usuarioService;

    @Override
    public BaseService<Usuario> getEjbInstance() {
        return this.usuarioService;
    }
}
