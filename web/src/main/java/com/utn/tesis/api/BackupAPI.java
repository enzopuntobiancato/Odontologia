package com.utn.tesis.api;

import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.BackupDTO;
import com.utn.tesis.service.BackupService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/backup")
@RequestScoped
public class BackupAPI {
    @Inject
    private BackupService backupService;

    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BackupDTO> findByFilters(@QueryParam("fechaGeneracion") String fechaGeneracionStr,
                                         @QueryParam("pageNumber") Long pageNumber,
                                         @QueryParam("pageSize") Long pageSize) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date fechaGeneracion = null;
        if (StringUtils.isNotBlank(fechaGeneracionStr)) {
            fechaGeneracion = sdf.parse(fechaGeneracionStr);
        }
        return backupService.findByFilters(fechaGeneracion, pageNumber, pageSize);
    }

    @PUT
    @Path("/generate")
    public Response generateNewBackup() throws SAPOException {
        backupService.generateNewBackup();
        return Response.ok().build();
    }

    @GET
    @Path("/getBackup")
    @Produces({"text/plain"})
    public Response getPdf(@QueryParam("idBackup") Long idBackup) {
        Response.ResponseBuilder response = Response.noContent();
        if (idBackup != null) {
            try {
                Pair<String, byte[]> file = backupService.findBackup(idBackup);
                response = Response.ok(file.getRight());
                response.header("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getLeft()));
            } catch (IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return response.build();
    }
}
