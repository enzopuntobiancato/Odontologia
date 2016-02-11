package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.model.TrabajoPractico;
import com.utn.tesis.service.TrabajoPracticoService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 12/05/15
 * Time: 20:50
 */
@Path("/trabajoPractico")
@RequestScoped
public class TrabajoPracticoAPI extends BaseAPI {

    @Inject
    private TrabajoPracticoService trabajoPracticoService;

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TrabajoPractico> findByFilters(@QueryParam("nombre") String nombre, @QueryParam("grupoPracticaId") Long grupoPracticaId,
                                               @QueryParam("practicaId") Long practicaId, @QueryParam("dadosBaja") boolean dadosBaja,
                                               @QueryParam("pageNumber") Long pageNumber, @QueryParam("pageSize") Long pageSize) {
        return trabajoPracticoService.findByFilters(nombre, grupoPracticaId, practicaId, dadosBaja, pageNumber, pageSize);
    }

}
