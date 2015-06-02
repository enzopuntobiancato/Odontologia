package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.service.BaseService;
import com.utn.tesis.service.CatedraService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/catedra")
@RequestScoped
public class CatedraAPI extends BaseAPI<Catedra> {

    @Inject
    CatedraService catedraService;

    @Override
    public BaseService<Catedra> getEjbInstance() {
        return catedraService;
    }

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catedra> findByFilters(@QueryParam("denominacion") String denominacion,
                                       @QueryParam("materiaId") Long materiaId,
                                       @QueryParam("dadosBaja") boolean dadosBaja,
                                       @QueryParam("pageNumber") Long pageNumber,
                                       @QueryParam("pageSize") Long pageSize) {
        List<Catedra> result = catedraService.findByFilters(denominacion, materiaId, dadosBaja, pageNumber, pageSize);
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setProfesores(null);
            result.get(i).setTrabajosPracticos(null);
        }
        return result;
    }

}
