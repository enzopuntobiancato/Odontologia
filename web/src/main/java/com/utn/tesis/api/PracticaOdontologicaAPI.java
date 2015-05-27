package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.model.PracticaOdontologica;
import com.utn.tesis.service.BaseService;
import com.utn.tesis.service.PracticaOdontologicaService;

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
 * User: Enzo
 * Date: 15/02/15
 * Time: 17:07
 */

@Path("/practicaOdontologica")
@RequestScoped
public class PracticaOdontologicaAPI extends BaseAPI<PracticaOdontologica> {

    @Inject
    private PracticaOdontologicaService practicaOdontologicaService;

    @Override
    public BaseService getEjbInstance() {
            return practicaOdontologicaService;
    }

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PracticaOdontologica> findByFilters(@QueryParam("denominacion") String denominacion,
                                       @QueryParam("idGrupoPractica") Long idGrupoPractica,
                                       @QueryParam("dadosBaja") boolean dadosBaja,
                                       @QueryParam("pageNumber") Long pageNumber,
                                       @QueryParam("pageSize") Long pageSize) {
        return practicaOdontologicaService.findByFilters(denominacion, idGrupoPractica, dadosBaja, pageNumber, pageSize);
    }

}
