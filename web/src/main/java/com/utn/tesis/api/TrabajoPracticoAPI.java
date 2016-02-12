package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.TrabajoPracticoDTO;
import com.utn.tesis.mapping.mapper.TrabajoPracticoMapper;
import com.utn.tesis.model.TrabajoPractico;
import com.utn.tesis.service.TrabajoPracticoService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 12/05/15
 * Time: 20:50
 */
@Path("/trabajoPractico")
@Slf4j
@RequestScoped
public class TrabajoPracticoAPI extends BaseAPI {

    @Inject
    private TrabajoPracticoService trabajoPracticoService;
    @Inject
    private TrabajoPracticoMapper trabajoPracticoMapper;

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TrabajoPractico> findByFilters(@QueryParam("nombre") String nombre, @QueryParam("grupoPracticaId") Long grupoPracticaId,
                                               @QueryParam("practicaId") Long practicaId, @QueryParam("dadosBaja") boolean dadosBaja,
                                               @QueryParam("pageNumber") Long pageNumber, @QueryParam("pageSize") Long pageSize) {
        return trabajoPracticoService.findByFilters(nombre, grupoPracticaId, practicaId, dadosBaja, pageNumber, pageSize);
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(TrabajoPracticoDTO dto){
        try{
            if (dto.getId() == null){
                TrabajoPractico entity = trabajoPracticoMapper.fromDTO(dto);
                trabajoPracticoService.save(entity);
            }else{
                this.update(dto);
            }
            return Response.ok(dto).build();
        }   catch (SAPOException se){
            return persistenceRequest(se);
        }   catch (Exception e){
            log.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    private void update(TrabajoPracticoDTO dto) {
    }

}
