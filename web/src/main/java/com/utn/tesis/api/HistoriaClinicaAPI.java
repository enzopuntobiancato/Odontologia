package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.HistoriaClinicaDTO;
import com.utn.tesis.mapping.mapper.HistoriaClinicaMapper;
import com.utn.tesis.model.HistoriaClinica;
import com.utn.tesis.service.HistoriaClinicaService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 1/05/16
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
@Path("/historiaClinica")
@RequestScoped
@Slf4j
public class HistoriaClinicaAPI extends BaseAPI{
    @Inject
    private HistoriaClinicaService historiaClinicaService;
    @Inject
    private HistoriaClinicaMapper historiaClinicaMapper;

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HistoriaClinicaDTO> findByFilters(@QueryParam("numero") Integer numero,
                                                  @QueryParam("fechaApertura") Calendar fechaApertura,
                                                  @QueryParam("realizoHC") String realizoHC,
                                                  @QueryParam("atencion") String atencion,
                                                  @QueryParam("diagnostico") String diagnostico,
                                                  @QueryParam("detalleHC") String detalle,
                                                  @QueryParam("pageNumber") Long pageNumber,
                                                  @QueryParam("pageSize") Long pageSize) {
        List<HistoriaClinica> result = historiaClinicaService.findByFilters(numero,fechaApertura,null,null,null,null,
                pageNumber,pageSize);
        return historiaClinicaMapper.toDTOList(result);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(HistoriaClinicaDTO dto) {
        try {
            HistoriaClinica entity = historiaClinicaMapper.fromDTO(dto);
            historiaClinicaService.save(entity);
            return Response.ok(dto).build();
        } catch (SAPOException se) {
            return persistenceRequest(se);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/findById")
    @GET
    public HistoriaClinicaDTO findById(@QueryParam("id") Long id) {
        HistoriaClinica entity = historiaClinicaService.findById(id);
        return historiaClinicaMapper.toDTO(entity);
    }

    @Path("/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(HistoriaClinicaDTO dto) {
        HistoriaClinicaDTO response;
        try {
            HistoriaClinica entity = historiaClinicaService.remove(Long.getLong("1"),"Lala");
            response = historiaClinicaMapper.toDTO(entity);
        } catch (SAPOException se) {
            return persistenceRequest(se);
        }
        return Response.ok(response).build();
    }

    @Path("/restore")
    @PUT
    public void restore(@QueryParam("id") Long id) {
        historiaClinicaService.restore(id);
    }
}
