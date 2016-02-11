package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.mapping.mapper.UsuarioConsultaMapper;
import com.utn.tesis.mapping.mapper.UsuarioMapper;
import com.utn.tesis.model.Persona;
import com.utn.tesis.model.Rol;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.CommonsService;
import com.utn.tesis.service.UsuarioService;
import com.utn.tesis.util.EncryptionUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
public class UsuarioAPI extends BaseAPI {

    @Inject
    UsuarioService usuarioService;
    @Inject
    UsuarioMapper usuarioMapper;
    @Inject
    PersonaMapper personaMapper;
    @Inject
    CommonsService commonsService;
    @Inject
    UsuarioConsultaMapper usuarioConsultaMapper;

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsuarioConsultaDTO> findByFilters(@QueryParam("nombreUsuario") String nombreUsuario,
                                       @QueryParam("email") String email,
                                       @QueryParam("rolId") Long rolId,
                                       @QueryParam("dadosBaja") boolean dadosBaja,
                                       @QueryParam("pageNumber") Long pageNumber,
                                       @QueryParam("pageSize") Long pageSize) {
        List<UsuarioConsultaDTO> result = new ArrayList<UsuarioConsultaDTO>();
        List<Usuario> usuarios = usuarioService.findByFilters(nombreUsuario, email, rolId, dadosBaja, pageNumber, pageSize);
        for (Usuario usuario : usuarios){
            UsuarioConsultaDTO usuarioConsultaDTO = usuarioConsultaMapper.personaToUsuarioConsultaDTO(usuarioService.findPersonaByUsuario(usuario));
            result.add(usuarioConsultaDTO);
        }
        return result;
    }

    @Path("/findById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioConsultaDTO findUsuarioById(@QueryParam("id") Long id){
        Usuario usuario = usuarioService.findById(id);
        Persona persona = usuarioService.findPersonaByUsuario(usuario);
        UsuarioConsultaDTO usuarioDTO = usuarioConsultaMapper.personaToUsuarioConsultaDTO(persona);
        return usuarioDTO;
    }

    @Path("/findPersona")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PersonaDTO findPersonaById(@QueryParam("id") Long id){
        Usuario usuario = usuarioService.findById(id);
        Persona persona = usuarioService.findPersonaByUsuario(usuario);
        PersonaDTO usuarioDTO = personaMapper.toDTO(persona);
        return usuarioDTO;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveUsuario")
    public Response save(PersonaDTO personaDTO) {
        //Usuario
        UsuarioDTO usuarioDTO = personaDTO.getUsuario();
        Usuario entityUsuario = usuarioMapper.fromUsuarioDTO(usuarioDTO);
        try {
            if (entityUsuario.isNew()){
                Rol rol =  commonsService.findRolById(usuarioDTO.getRol().getId());
                entityUsuario.setRol(rol);
                Persona entityPersona = personaMapper.fromDTO(personaDTO);
                entityUsuario.setNombreUsuario(personaDTO.getDocumento().getNumero()); //Por defaulr, el nombre de usuario es el numero de documento.
                usuarioService.saveUsuario(entityPersona, entityUsuario);
            }
            else {
                this.update(usuarioDTO);
            }
            return Response.ok(usuarioDTO).build();
        } catch (SAPOException se) {
            return persistenceRequest(se);
        } catch (Exception e) {
            Logger.getLogger(BaseAPI.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void update(UsuarioDTO usuarioDTO) throws SAPOException {
        Usuario persistedEntity = usuarioService.findById(usuarioDTO.getId());
        persistedEntity.setNombreUsuario(usuarioDTO.getNombreUsuario());
        persistedEntity.setEmail(usuarioDTO.getEmail());

        if (usuarioDTO.getPassword() != null) {
            try {
                persistedEntity.setContrasenia(EncryptionUtils.encryptMD5A1(usuarioDTO.getPassword()));
            } catch (NoSuchAlgorithmException e) {
                persistedEntity.setContrasenia(EncryptionUtils.encryptMD5A2(usuarioDTO.getPassword()));
            }
        }

        usuarioService.update(persistedEntity);
    }


}
