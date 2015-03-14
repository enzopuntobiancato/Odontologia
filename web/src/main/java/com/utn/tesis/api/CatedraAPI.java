package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.service.CatedraService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/catedra")
@RequestScoped
public class CatedraAPI extends BaseAPI {

    @Inject
    CatedraService catedraService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response create(Catedra catedra) {
        try {
            catedra = catedraService.create(catedra);
        } catch (Exception e)
        {
            return persistenceRequest(e);
        }

        return Response.ok(catedra).build();
    }
}
