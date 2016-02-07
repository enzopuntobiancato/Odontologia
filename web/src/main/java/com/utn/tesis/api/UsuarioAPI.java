package com.utn.tesis.api;

import com.utn.tesis.annotation.JsonMap;
import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.UsuarioDTO;
import com.utn.tesis.mapping.mapper.UsuarioMapper;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.BaseService;
import com.utn.tesis.service.UsuarioService;
import com.utn.tesis.util.EncryptionUtils;
import com.utn.tesis.util.MappingUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: Enzo
 * Date: 30/06/15
 * Time: 22:04
 */
@Path("/usuario")
@RequestScoped
public class UsuarioAPI extends BaseAPI<Usuario> {

    @Inject
    UsuarioService usuarioService;
    @Inject
    UsuarioMapper usuarioMapper;

    @Override
    public BaseService<Usuario> getEjbInstance() {
        return this.usuarioService;
    }

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsuarioDTO> findByFilters(@QueryParam("nombreUsuario") String nombreUsuario,
                                       @QueryParam("email") String email,
                                       @QueryParam("rolId") Long rolId,
                                       @QueryParam("dadosBaja") boolean dadosBaja,
                                       @QueryParam("pageNumber") Long pageNumber,
                                       @QueryParam("pageSize") Long pageSize) {
        List<UsuarioDTO> result = usuarioService.findByFilters(nombreUsuario, email, rolId, dadosBaja, pageNumber, pageSize);

        return result;
    }

    @Override
    public Usuario findById(@QueryParam("id") Long id) {
        return (Usuario) MappingUtil.serializeWithView(super.findById(id), JsonMap.Public.class);
    }

    @Path("/findById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioDTO findUsuarioById(@QueryParam("id") Long id){
        UsuarioDTO usuarioDTO = usuarioService.findUsuarioById(id);
        return usuarioDTO;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveUsuario")
    public Response save(UsuarioDTO usuarioDTO) {
        try {
            if (usuarioDTO.getId() == 0){
                usuarioService.create(usuarioDTO);
            }
            else {
                this.update(usuarioDTO);
            }
           /* if (entity.isNew()) {
                entity = getEjbInstance().create(entity);
            } else {
                this.update(entity);
            }
            entity = (Usuario) MappingUtil.serializeWithView(entity, JsonMap.Public.class);*/
            return Response.ok(usuarioDTO).build();
        } catch (SAPOException se) {
            return persistenceRequest(se);
        } catch (Exception e) {
            Logger.getLogger(BaseAPI.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void update(UsuarioDTO usuarioDTO) throws SAPOException {
        Usuario persistedEntity = getEjbInstance().findById(usuarioDTO.getId());

        persistedEntity.setNombreUsuario(usuarioDTO.getNombreUsuario());
        persistedEntity.setEmail(usuarioDTO.getEmail());

        if (usuarioDTO.getContrasenia() != null) {
            try {
                persistedEntity.setContrasenia(EncryptionUtils.encryptMD5A1(usuarioDTO.getContrasenia()));
            } catch (NoSuchAlgorithmException e) {
                persistedEntity.setContrasenia(EncryptionUtils.encryptMD5A2(usuarioDTO.getContrasenia()));
            }
        }

        getEjbInstance().update(persistedEntity);
    }
}
