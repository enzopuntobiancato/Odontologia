package com.utn.tesis.api.commons;

import com.utn.tesis.mapping.dto.BarrioDTO;
import com.utn.tesis.mapping.dto.CiudadDTO;
import com.utn.tesis.mapping.dto.ProvinciaDTO;
import com.utn.tesis.mapping.mapper.BarrioMapper;
import com.utn.tesis.mapping.mapper.CiudadMapper;
import com.utn.tesis.mapping.mapper.ProvinciaMapper;
import com.utn.tesis.model.Barrio;
import com.utn.tesis.model.Ciudad;
import com.utn.tesis.model.Provincia;
import com.utn.tesis.service.BarrioService;
import com.utn.tesis.service.CiudadService;
import com.utn.tesis.service.ProvinciaService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 4/03/16
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
@Path("/location")
@RequestScoped
public class LocationAPI {
    @Inject
    private ProvinciaService provinciaService;
    @Inject
    private CiudadService ciudadService;
    @Inject
    private BarrioService barrioService;
    @Inject
    private ProvinciaMapper provinciaMapper;
    @Inject
    private CiudadMapper ciudadMapper;
    @Inject
    private BarrioMapper barrioMapper;

    @Path("/getProvincias")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProvinciaDTO> getProvincias() {
         return provinciaMapper.toDTOList(provinciaService.findAll());
    }

    @Path("/getCiudades")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CiudadDTO> getCiudades(){
        return ciudadMapper.toDTOList(ciudadService.findAll());
    }

    @Path("/getBarrios")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BarrioDTO> getBarrios(){
        return barrioMapper.toDTOList(barrioService.findAll());
    }
}
