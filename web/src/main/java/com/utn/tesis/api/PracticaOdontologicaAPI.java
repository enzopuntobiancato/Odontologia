package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.PracticaOdontologicaDTO;
import com.utn.tesis.mapping.mapper.PracticaOdontologicaMapper;
import com.utn.tesis.model.PracticaOdontologica;
import com.utn.tesis.service.PracticaOdontologicaService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 15/02/15
 * Time: 17:07
 */

@Path("/practicaOdontologica")
@RequestScoped
@Slf4j
public class PracticaOdontologicaAPI extends BaseAPI {

    @Inject
    private PracticaOdontologicaService practicaOdontologicaService;
    @Inject
    private PracticaOdontologicaMapper practicaMapper;

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PracticaOdontologicaDTO> findByFilters(@QueryParam("denominacion") String denominacion,
                                                       @QueryParam("idGrupoPractica") Long idGrupoPractica,
                                                       @QueryParam("dadosBaja") boolean dadosBaja,
                                                       @QueryParam("pageNumber") Long pageNumber,
                                                       @QueryParam("pageSize") Long pageSize) {
        List<PracticaOdontologica> entities = practicaOdontologicaService.findByFilters(denominacion, idGrupoPractica, dadosBaja, pageNumber, pageSize);
        return practicaMapper.toDTOList(entities);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(PracticaOdontologicaDTO dto) {
        try {
            dto = practicaOdontologicaService.save(dto);
            return Response.ok(dto).build();
        } catch (SAPOException se) {
            return persistenceRequest(se);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(PracticaOdontologicaDTO dto) {
        try {
            PracticaOdontologica entity = practicaOdontologicaService.remove(dto.getId(), dto.getMotivoBaja());
            dto = practicaMapper.toDTO(entity);
        } catch (SAPOException se) {
            return persistenceRequest(se);
        }
        return Response.ok(dto).build();
    }

    @Path("/restore")
    @PUT
    public void restore(@QueryParam("id") Long id) {
        practicaOdontologicaService.restore(id);
    }

    @Path("/findById")
    @GET
    public PracticaOdontologicaDTO findById(@QueryParam("id") Long id) {
        PracticaOdontologica entity = practicaOdontologicaService.findById(id);
        return practicaMapper.toDTO(entity);
    }

    @Path("/findAll")
    @GET
    public List<PracticaOdontologicaDTO> findAll() {
        return practicaMapper.toDTOList(practicaOdontologicaService.findAll());
    }

}
