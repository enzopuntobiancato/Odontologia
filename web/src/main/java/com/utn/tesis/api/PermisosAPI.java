package com.utn.tesis.api;

import com.utn.tesis.mapping.dto.FuncionalidadDTO;
import com.utn.tesis.mapping.dto.RolEditDTO;
import com.utn.tesis.service.FuncionalidadService;
import com.utn.tesis.service.PrivilegioService;
import com.utn.tesis.service.RolService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/permisos")
@RequestScoped
public class PermisosAPI {
    @Inject
    private FuncionalidadService funcionalidadService;
    @Inject
    private PrivilegioService privilegioService;
    @Inject
    private RolService rolService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public void savePermisos(List<RolEditDTO> roles) {
        rolService.savePermisos(roles);
    }

    @GET
    @Path("/findAllFuncionalidades")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FuncionalidadDTO> findAllFuncionalidades() {
        return funcionalidadService.findAllFuncionalidades();
    }

    @GET
    @Path("/findAllRoles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RolEditDTO> findAllRoles() {
        return rolService.findAllRoles();
    }
}
