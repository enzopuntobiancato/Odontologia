package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.PersonaDTO;
import com.utn.tesis.mapping.dto.UsuarioConsultaDTO;
import com.utn.tesis.mapping.dto.UsuarioDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.mapping.mapper.UsuarioConsultaMapper;
import com.utn.tesis.mapping.mapper.UsuarioMapper;
import com.utn.tesis.model.Persona;
import com.utn.tesis.model.Rol;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.CommonsService;
import com.utn.tesis.service.UsuarioService;
import com.utn.tesis.util.EncryptionUtils;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Enzo
 * Date: 30/06/15
 * Time: 22:04
 */
@Path("/usuario")
@RequestScoped
@Slf4j
public class UsuarioAPI extends BaseAPI {

    @Inject
    private UsuarioService usuarioService;
    @Inject
    private UsuarioMapper usuarioMapper;
    @Inject
    private PersonaMapper personaMapper;
    @Inject
    private CommonsService commonsService;
    @Inject
    private UsuarioConsultaMapper usuarioConsultaMapper;

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
        for (Usuario usuario : usuarios) {
            UsuarioConsultaDTO usuarioConsultaDTO = usuarioConsultaMapper.personaToUsuarioConsultaDTO(usuarioService.findPersonaByUsuario(usuario));
            result.add(usuarioConsultaDTO);
        }
        return result;
    }

    @Path("/findById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioConsultaDTO findUsuarioById(@QueryParam("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        Persona persona = usuarioService.findPersonaByUsuario(usuario);
        UsuarioConsultaDTO usuarioDTO = usuarioConsultaMapper.personaToUsuarioConsultaDTO(persona);
        return usuarioDTO;
    }

    @Path("/findPersona")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PersonaDTO findPersonaById(@QueryParam("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        Persona persona = usuarioService.findPersonaByUsuario(usuario);
        PersonaDTO usuarioDTO = personaMapper.toDTO(persona);
        return usuarioDTO;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveUsuario")
    public Response save(PersonaDTO personaDTO) throws SAPOException {
        UsuarioDTO usuarioDTO = personaDTO.getUsuario();
        Usuario entityUsuario = usuarioMapper.fromUsuarioDTO(usuarioDTO);
        if (entityUsuario.isNew()) {
            Rol rol = commonsService.findRolById(usuarioDTO.getRol().getId());
            entityUsuario.setRol(rol);
            Persona entityPersona = personaMapper.fromDTO(personaDTO);
            entityUsuario.setNombreUsuario(personaDTO.getDocumento().getNumero()); //Por defaulr, el nombre de usuario es el numero de documento.
            usuarioService.saveUsuario(entityPersona, entityUsuario);
        } else {
            this.update(usuarioDTO);
        }
        return Response.ok(usuarioDTO).build();
    }

    @Path("/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(UsuarioConsultaDTO dto) throws SAPOException {
        Usuario entity = usuarioService.remove(dto.getId(), dto.getMotivoBaja());
        return Response.ok(dto).build();
    }

    @Path("/restore")
    @PUT
    public void restore(@QueryParam("id") Long id) {
        usuarioService.restore(id);
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

    @Path("/findByIdAsUsuarioLogueadoBean")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioLogueadoDTO findById(@QueryParam("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        usuario.getRol().setPrivilegios(null);
        usuario.setAuthToken(null);
        return usuarioMapper.toUsuarioLogueadoDTO(usuario);
    }

}
