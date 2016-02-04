package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.ArchivoDTO;
import com.utn.tesis.mapping.dto.PersonaDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.FileExtension;
import com.utn.tesis.model.Persona;
import com.utn.tesis.service.BaseService;
import com.utn.tesis.service.PersonaService;
import com.utn.tesis.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * User: Enzo
 * Date: 29/08/15
 * Time: 18:18
 */
@Path("/persona")
@RequestScoped
@Slf4j
public class PersonaAPI extends BaseAPI<Persona> {

    @Inject
    private PersonaService personaService;
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private PersonaMapper personaMapper;

    @Override
    public BaseService<Persona> getEjbInstance() {
        return this.personaService;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveUserRelatedData")
    public Response save(MultipartFormDataInput input) throws SAPOException {
        Map<String, List<InputPart>> form = input.getFormDataMap();
        UsuarioLogueadoDTO usuario = (UsuarioLogueadoDTO) retrieveObject(form, "usuario", UsuarioLogueadoDTO.class);
        PersonaDTO person = (PersonaDTO) retrieveObject(form, "persona", UsuarioLogueadoDTO.rolToPerson.get(usuario.getRol().toUpperCase()));

        Map<String, Object> file = retrieveFile(form, "file");
        ArchivoDTO imagenUsuario = null;
        if (file != null) {
            imagenUsuario = new ArchivoDTO();
            imagenUsuario.setExtension((FileExtension) file.get(EXTENSION));
            imagenUsuario.setNombre((String) file.get(NAME));
            imagenUsuario.setArchivo((InputStream) file.get(FILE));
        }
        personaService.update(person, imagenUsuario);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/findByUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PersonaDTO findByUser(@QueryParam("username") String username,
                                 @QueryParam("authToken") String authToken) {
        Persona persona = personaService.findById(1L);
        return personaMapper.toDTO(persona);
    }
}
