package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.service.BaseService;
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
public class CatedraAPI extends BaseAPI<Catedra> {

    @Inject
    CatedraService catedraService;

    @Override
    public BaseService<Catedra> getEjbInstance() {
        return catedraService;
    }

}
