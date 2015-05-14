package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;
import com.utn.tesis.service.BaseService;
import com.utn.tesis.service.MateriaService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/materia")
@RequestScoped
public class MateriaAPI extends BaseAPI<Materia> {

    @Inject
    private MateriaService materiaService;


    @Override
    public BaseService<Materia> getEjbInstance() {
        return materiaService;
    }

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Materia> findByFilters(@QueryParam("nombre") String nombre,
                                       @QueryParam("nivel") String nivel,
                                       @QueryParam("dadosBaja") boolean dadosBaja,
                                       @QueryParam("pageNumber") Long pageNumber,
                                       @QueryParam("pageSize") Long pageSize) {
        return materiaService.findByFilters(nombre, nivel != null ? Nivel.valueOf(nivel) : null, dadosBaja, pageNumber, pageSize);
    }
}
