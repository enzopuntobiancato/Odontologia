package com.utn.tesis.api;

import com.utn.tesis.SessionHelper;
import com.utn.tesis.api.commons.MultiPartFormHelper;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.AtencionMapper;
import com.utn.tesis.model.FileExtension;
import com.utn.tesis.service.AtencionService;
import com.utn.tesis.service.CatedraService;
import com.utn.tesis.service.PracticaOdontologicaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("/atencion")
@Slf4j
@RequestScoped
public class AtencionAPI {
    @Inject
    private AtencionService atencionService;
    @Inject
    private MultiPartFormHelper helper;
    @Inject
    private SessionHelper sessionHelper;
    @Inject
    private CatedraService catedraService;
    @Inject
    private PracticaOdontologicaService practicaOdontologicaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find/{id}")
    public List<AtencionDTO> find(@PathParam("id") Long pacienteId,
                                  @QueryParam("practicaId") Long practicaId,
                                  @QueryParam("catedraId") Long catedraId,
                                  @QueryParam("trabajoPracticoId") Long trabajoPracticoId,
                                  @QueryParam("fechaDesde") String fechaDesdeStr,
                                  @QueryParam("fechaHasta") String fechaHastaStr,
                                  @QueryParam("pageNumber") Long pageNumber,
                                  @QueryParam("pageSize") Long pageSize) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date fechaDesde = null;
        Date fechaHasta = null;
        if (StringUtils.isNotBlank(fechaDesdeStr)) {
            fechaDesde = sdf.parse(fechaDesdeStr);
        }
        if(StringUtils.isNotBlank(fechaHastaStr)) {
            fechaHasta = sdf.parse(fechaHastaStr);
        }
        return atencionService.findByFilters(pacienteId, practicaId, catedraId, trabajoPracticoId, fechaDesde, fechaHasta, pageNumber, pageSize);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveRelatedDocs")
    public Response saveRelatedDocs(MultipartFormDataInput input, @QueryParam("atencionId") Long atencionId) throws SAPOException {
        Map<String, List<InputPart>> form = input.getFormDataMap();
        List<Map<String, Object>> files = helper.retrieveFiles(form, "files");
        List<ArchivoDTO> documentaciones = new ArrayList<ArchivoDTO>();
        for (Map<String, Object> file: files) {
            ArchivoDTO archivoDTO = new ArchivoDTO();
            archivoDTO.setExtension((FileExtension) file.get(helper.EXTENSION));
            archivoDTO.setNombre((String) file.get(helper.NAME));
            archivoDTO.setArchivo((InputStream) file.get(helper.FILE));
            documentaciones.add(archivoDTO);
        }
        atencionService.saveRelatedDocs(atencionId, documentaciones);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(@Context HttpServletRequest request, AtencionDTO atencion) throws SAPOException {
        UsuarioLogueadoDTO usuarioLogueadoDTO = sessionHelper.getUser(request);
        Long atencionId = atencionService.save(atencion, usuarioLogueadoDTO);
        return Response.ok(atencionId).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findCatedrasByPractica")
    public List<CatedraConsultaDTO> findCatedrasByPractica(@QueryParam("practicaId") Long practicaId) {
        return catedraService.findCatedrasByPractica(practicaId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findPracticas")
    public List<PracticaOdontologicaDTO> findPracticasByFilters(@QueryParam("text") String denominacion,
                                                                @QueryParam("catedraId") Long catedraId,
                                                                @QueryParam("practicoId") Long trabajoPracticoId) {
        return practicaOdontologicaService.findByDenominacionCatedraAndPractico(denominacion, catedraId, trabajoPracticoId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findDocumentaciones")
    public List<ArchivoDTO> findDocumentaciones(@QueryParam("atencionId") Long atencionId) {
        return atencionService.findDocumentacionesByAtencion(atencionId);
    }
}
