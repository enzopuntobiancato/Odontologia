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
    public Response save(PracticaOdontologicaDTO dto) throws SAPOException {
        dto = practicaOdontologicaService.save(dto);
        return Response.ok(dto).build();
    }

    @Path("/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(PracticaOdontologicaDTO dto) throws SAPOException {
        PracticaOdontologica entity = practicaOdontologicaService.remove(dto.getId(), dto.getMotivoBaja());
        dto = practicaMapper.toDTO(entity);
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

    @Path("/findByDenominacion")
    @GET
    public List<PracticaOdontologicaDTO> findByDenominacion(@QueryParam("text") String searchText) {
        return practicaOdontologicaService.findByDenominacion(searchText);
    }
}
