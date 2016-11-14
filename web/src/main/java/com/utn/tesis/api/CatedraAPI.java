package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.CatedraConsultaDTO;
import com.utn.tesis.mapping.dto.CatedraDTO;
import com.utn.tesis.mapping.mapper.CatedraMapper;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.service.CatedraService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/catedra")
@RequestScoped
@Slf4j
public class CatedraAPI extends BaseAPI {

    @Inject
    private CatedraService catedraService;
    @Inject
    private CatedraMapper catedraMapper;

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CatedraDTO> findByFilters(@QueryParam("denominacion") String denominacion,
                                                  @QueryParam("materiaId") Long materiaId,
                                                  @QueryParam("dadosBaja") boolean dadosBaja,
                                                  @QueryParam("pageNumber") Long pageNumber,
                                                  @QueryParam("pageSize") Long pageSize) {
        List<Catedra> result = catedraService.findByFilters(denominacion, materiaId, dadosBaja, pageNumber, pageSize);
        result = catedraService.reloadList(result,1);
        return catedraMapper.toDTOList(result);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(CatedraDTO dto) throws SAPOException {
        dto = catedraService.save(dto);
        return Response.ok(dto).build();

    }

    @Path("/findById")
    @GET
    public CatedraDTO findById(@QueryParam("id") Long id) {
        Catedra entity = catedraService.findById(id);
        return catedraMapper.toDTO(entity);
    }

    @Path("/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(CatedraDTO dto) throws SAPOException {
        Catedra entity = catedraService.remove(dto.getId(), dto.getMotivoBaja());
        CatedraConsultaDTO response = catedraMapper.toConsultaDTO(entity);
        return Response.ok(response).build();
    }

    @Path("/restore")
    @PUT
    public void restore(@QueryParam("id") Long id) {
        catedraService.restore(id);
    }

    @Path("/findCatedrasByDenomicacion")
    @GET
    public List<CatedraConsultaDTO> findCatedrasByDenomicacion(@QueryParam("searchText") String searchText){
        List<Catedra> catedras = catedraService.findCatedrasByDenomicacion(searchText);
        List<CatedraConsultaDTO> catedraDTOs = catedraMapper.toConsultaDTOList(catedras);
        return catedraDTOs;
    }

    @GET
    @Path("/findAll")
    public List<CatedraDTO> findAllCatedras() {
        return catedraService.findAllDTOs();
    }
}
