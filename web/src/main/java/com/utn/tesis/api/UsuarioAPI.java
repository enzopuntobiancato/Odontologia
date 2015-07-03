package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.BaseService;
import com.utn.tesis.service.UsuarioService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> findByFilters(@QueryParam("nombreUsuario") String nombreUsuario,
                                       @QueryParam("email") String email,
                                       @QueryParam("rolId") Long rolId,
                                       @QueryParam("dadosBaja") boolean dadosBaja,
                                       @QueryParam("pageNumber") Long pageNumber,
                                       @QueryParam("pageSize") Long pageSize) {
        List<Usuario> result = usuarioService.findByFilters(nombreUsuario, email, rolId, dadosBaja, pageNumber, pageSize);
//        for (int i = 0; i < result.size(); i++) {
//            result.get(i).setProfesores(null);
//            result.get(i).setTrabajosPracticos(null);
//        }
        return result;
    }
}
