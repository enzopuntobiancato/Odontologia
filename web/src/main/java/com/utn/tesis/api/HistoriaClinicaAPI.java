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
import java.util.Date;
import java.util.List;

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
                                                  @QueryParam("fechaApertura") Date fechaApertura,
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
    public Response save(HistoriaClinicaDTO dto) throws SAPOException {
        HistoriaClinica entity = historiaClinicaMapper.fromDTO(dto);
        historiaClinicaService.save(entity);
        return Response.ok(dto).build();
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
    public Response remove(HistoriaClinicaDTO dto) throws SAPOException {
        HistoriaClinica entity = historiaClinicaService.remove(Long.getLong("1"),"Lala");
        HistoriaClinicaDTO response = historiaClinicaMapper.toDTO(entity);
        return Response.ok(response).build();
    }

    @Path("/restore")
    @PUT
    public void restore(@QueryParam("id") Long id) {
        historiaClinicaService.restore(id);
    }
}
