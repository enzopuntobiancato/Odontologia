package com.utn.tesis.api;

import com.utn.tesis.api.commons.MultiPartFormHelper;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.FileExtension;
import com.utn.tesis.model.Persona;
import com.utn.tesis.model.Profesor;
import com.utn.tesis.service.PersonaService;
import com.utn.tesis.service.ProfesorService;
import com.utn.tesis.service.RolService;
import com.utn.tesis.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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

@Path("/persona")
@RequestScoped
@Slf4j
public class PersonaAPI {

    @Inject
    private PersonaService personaService;
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private ProfesorService profesorService;
    @Inject
    private PersonaMapper personaMapper;
    @Inject
    private MultiPartFormHelper helper;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveUserRelatedData")
    public Response save(MultipartFormDataInput input) throws SAPOException {
        Map<String, List<InputPart>> form = input.getFormDataMap();
        UsuarioLogueadoDTO usuario = (UsuarioLogueadoDTO) helper.retrieveObject(form, "usuario", UsuarioLogueadoDTO.class);
        PersonaDTO person = (PersonaDTO) helper.retrieveObject(form, "persona", RolService.rolToPerson.get(usuario.getRol().getKey()));

        Map<String, Object> file = helper.retrieveFile(form, "file");
        ArchivoDTO imagenUsuario = null;
        if (file != null) {
            imagenUsuario = new ArchivoDTO();
            imagenUsuario.setExtension((FileExtension) file.get(helper.EXTENSION));
            imagenUsuario.setNombre((String) file.get(helper.NAME));
            imagenUsuario.setArchivo((InputStream) file.get(helper.FILE));
        }
        personaService.update(person, imagenUsuario);
        UsuarioLogueadoDTO updatedUser = usuarioService.fetchUser(person.getUsuario().getId(), usuario.getRol());
        return Response.ok(updatedUser).build();
    }

    @Path("/findByUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PersonaDTO findByUser(@QueryParam("username") String username,
                                 @QueryParam("authToken") String authToken,
                                 @QueryParam("rol") String rol) {
        return personaService.findByUserByUsernameAndAuthtoken(username, authToken, rol);
    }

    @Path("/addCatedraToProfesor")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCatedraToProfesor(ProfesorDTO profesorDTO){
        try {
            Profesor updatedProfesor = personaMapper.profesorFromDTO(profesorDTO);
            Persona profesor = personaService.findById(profesorDTO.getId());
            ((Profesor) profesor).setCatedras(updatedProfesor.getCatedras());
            personaService.save(profesor);
            return Response.ok().build();
        } catch (SAPOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Path("/findProfesoresByApellido")
    @GET
    public List<ProfesorDTO> findProfesoresByNombre(@QueryParam("searchText") String searchText){
        String legajo = null;
        String apellidoNombre = null;

        if(StringUtils.isNumeric(searchText)){
            legajo = searchText;
        }else{
            apellidoNombre = searchText;
        }
        List<Profesor> profesores = profesorService.findProfesoresByApellido(apellidoNombre, legajo);

        List<ProfesorDTO> dtos = (List<ProfesorDTO>) personaMapper.toDTOList(profesores);
        return dtos;
    }
}
