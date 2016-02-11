package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.MateriaDTO;
import com.utn.tesis.mapping.mapper.MateriaMapper;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;
import com.utn.tesis.service.MateriaService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/materia")
@RequestScoped
@Slf4j
public class MateriaAPI extends BaseAPI {

    @Inject
    private MateriaService materiaService;
    @Inject
    private MateriaMapper materiaMapper;

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MateriaDTO> findByFilters(@QueryParam("nombre") String nombre,
                                          @QueryParam("nivel") String nivel,
                                          @QueryParam("dadosBaja") boolean dadosBaja,
                                          @QueryParam("pageNumber") Long pageNumber,
                                          @QueryParam("pageSize") Long pageSize) {
        return materiaService.findByFilters(nombre, nivel != null ? Nivel.valueOf(nivel) : null, dadosBaja, pageNumber, pageSize);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(MateriaDTO dto) {
        try {
            Materia entity;
            if (dto.getId() == null) {
                entity = materiaMapper.fromDTO(dto);
            } else {
                entity = materiaService.findById(dto.getId());
                materiaMapper.updateFromDTO(dto, entity);
            }

            dto = materiaMapper.toDTO(materiaService.save(entity));
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
    public Response remove(MateriaDTO dto) {
        try {
            Materia entity = materiaService.remove(dto.getId(), dto.getMotivoBaja());
            dto = materiaMapper.toDTO(entity);
        } catch (SAPOException se) {
            return persistenceRequest(se);
        }
        return Response.ok(dto).build();
    }

    @Path("/restore")
    @PUT
    public void restore(@QueryParam("id") Long id) {
        materiaService.restore(id);
    }

    @Path("/findById")
    @GET
    public MateriaDTO findById(@QueryParam("id") Long id) {
        Materia entity = materiaService.findById(id);
        return materiaMapper.toDTO(entity);
    }

    @Path("/findAll")
    @GET
    public List<MateriaDTO> findAll() {
        return materiaMapper.toDTOList(materiaService.findAll());
    }

}
