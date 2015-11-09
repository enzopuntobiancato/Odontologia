package com.utn.tesis.api;

import com.utn.tesis.annotation.JsonMap;
import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.mapping.dto.PersonaDTO;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.Administrador;
import com.utn.tesis.model.Archivo;
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
import java.util.Calendar;
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
    public Response save(MultipartFormDataInput input) {
        Map<String, List<InputPart>> form = input.getFormDataMap();
//        PersonaDTO person = (PersonaDTO) retrieveObject(form, "persona", PersonaDTO.class);
//        Usuario user = person.getUsuario();
//        person.setUsuario(usuarioService.findByUsernameAndAuthToken(user.getNombreUsuario(), user.getAuthToken()));
        Map<String, Object> file = retrieveFile(form, "file");
        if (file != null) {
            Archivo archivo = new Archivo();
            archivo.setExtension((FileExtension) file.get(EXTENSION));
            archivo.setNombre((String) file.get(NAME));
//            archivo.setFile((InputStream) file.get(FILE));
//            person.getUsuario().setImagenUsuario(archivo);
        }
        returningView = JsonMap.Public.class;
//        return super.save(person);
        return null;
    }

    @Path("/findByUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PersonaDTO findByUser(@QueryParam("username") String username,
                                 @QueryParam("authToken") String authToken) {

        Administrador administrador = new Administrador();
        administrador.setNombre("Enzo");
        administrador.setApellido("Biancato");
        Calendar c = Calendar.getInstance();
        c.set(1989, Calendar.AUGUST, 9);
        administrador.setFechaNacimiento(c);
        return personaMapper.adminToDTO(administrador);
    }
}
