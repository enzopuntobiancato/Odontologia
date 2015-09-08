package com.utn.tesis.api;

import com.utn.tesis.annotation.JsonMap;
import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.model.Alumno;
import com.utn.tesis.model.Persona;
import com.utn.tesis.service.BaseService;
import com.utn.tesis.service.PersonaService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 29/08/15
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
 */
@Path("/persona")
@RequestScoped
public class PersonaAPI extends BaseAPI<Persona> {

    @Inject
    PersonaService personaService;

    @Override
    public BaseService<Persona> getEjbInstance() {
        return this.personaService;
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(Persona entity) {
        returningView = JsonMap.Public.class;
        return super.save(entity);
    }
}
