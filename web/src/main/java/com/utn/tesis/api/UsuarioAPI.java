package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.api.commons.MultiPartFormHelper;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mail.MailService;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.mapping.mapper.UsuarioConsultaMapper;
import com.utn.tesis.mapping.mapper.UsuarioMapper;
import com.utn.tesis.model.FileExtension;
import com.utn.tesis.model.Persona;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.CommonsService;
import com.utn.tesis.service.RolService;
import com.utn.tesis.service.UsuarioService;
import com.utn.tesis.util.EncryptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

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
    @Inject
    private MailService mailService;
    @Inject
    private RolService rolService;
    @Inject
    private MultiPartFormHelper multiPartFormHelper;

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsuarioConsultaDTO> findByFilters(@QueryParam("nombreUsuario") String nombreUsuario,
                                                  @QueryParam("email") String email,
                                                  @QueryParam("rolId") Long rolId,
                                                  @QueryParam("dadosBaja") boolean dadosBaja,
                                                  @QueryParam("pageNumber") Long pageNumber,
                                                  @QueryParam("pageSize") Long pageSize) {
        List<UsuarioConsultaDTO> result = usuarioService.findByFilters(nombreUsuario, email, rolId, dadosBaja, pageNumber, pageSize);
        return result;
    }

    @Path("/findById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioConsultaDTO findUsuarioById(@QueryParam("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        Persona persona = usuarioService.findPersonaByUsuario(usuario).get(0);
        UsuarioConsultaDTO usuarioDTO = usuarioConsultaMapper.personaToUsuarioConsultaDTO(persona);
        return usuarioDTO;
    }

    @Path("/findPersona")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PersonaDTO findPersonaById(@QueryParam("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        Persona persona = usuarioService.findPersonaByUsuario(usuario).get(0);
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
        Persona entityPersona = personaMapper.fromDTO(personaDTO);
        entityUsuario.setNombreUsuario(personaDTO.getDocumento().getNumero()); //Por defaulr, el nombre de usuario es el numero de documento.
        String password = usuarioService.saveUsuario(entityPersona, entityUsuario);
        mailService.sendRegistrationMail(entityUsuario.getEmail(), entityPersona.getNombre(), entityUsuario.getNombreUsuario(), password);
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

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response save(MultipartFormDataInput input) throws SAPOException {
        Map<String, List<InputPart>> form = input.getFormDataMap();
        UsuarioViewEditDTO usuario = (UsuarioViewEditDTO) multiPartFormHelper.retrieveObject(form, "usuario", UsuarioViewEditDTO.class);

        Map<String, Object> file = multiPartFormHelper.retrieveFile(form, "file");
        ArchivoDTO imagenUsuario = null;
        if (file != null) {
            imagenUsuario = new ArchivoDTO();
            imagenUsuario.setExtension((FileExtension) file.get(multiPartFormHelper.EXTENSION));
            imagenUsuario.setNombre((String) file.get(multiPartFormHelper.NAME));
            imagenUsuario.setArchivo((InputStream) file.get(multiPartFormHelper.FILE));
        }
        try {
            usuarioService.updateUser(usuario, imagenUsuario);
        } catch (IllegalAccessException e) {
            throw new SAPOException(e);
        } catch (InstantiationException e) {
            throw new SAPOException(e);
        }
        return Response.ok().build();
    }

    @Path("/findByIdAsUsuarioLogueadoBean")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioLogueadoDTO findById(@QueryParam("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        usuario.setAuthToken(null);
        return UsuarioLogueadoDTO.valueOf(usuario);
    }

    @Path("/findRolesByUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RolUsuarioDTO> findRolesByUser(@QueryParam("id") Long userId) {
        return usuarioService.findRolesByUser(userId);
    }

    @GET
    @Path("/findPermisosRol")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PrivilegioDTO> findPrivilegiosByRol(@QueryParam("rol") String rol) {
        return rolService.findPrivilegiosByRolKey(rol);
    }

    @GET
    @Path("/findUsuarioView")
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioViewEditDTO findPrivilegiosByRol(@QueryParam("id") Long usuarioId) {
        return usuarioService.findUsuarioView(usuarioId);
    }

    @PUT
    @Path("/recuperarPassword")
    public Response recuperarPassword(@QueryParam("email") String email) throws SAPOException {
        usuarioService.recuperarPassword(email);
        return Response.ok().build();
    }
}
