package com.utn.tesis.api.commons;

import com.utn.tesis.model.Dia;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;
import com.utn.tesis.service.CommonsService;
import com.utn.tesis.service.MateriaService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/commons")
@RequestScoped
public class CommonsAPI {

    @Inject
    CommonsService commonsService;

    @Inject
    MateriaService materiaService;

    @Path("/getNiveles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Nivel> findAllNiveles() {
        return commonsService.findAllNiveles();
    }

    @Path("/getMaterias")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Materia> findAllMaterias() {
        return materiaService.findAll();
    }

    @Path("/getDias")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Dia> findAllDias() {
        return commonsService.findAllDias();
    }
}
