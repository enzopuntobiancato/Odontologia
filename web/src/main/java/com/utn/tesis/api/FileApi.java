package com.utn.tesis.api;

import com.utn.tesis.mapping.dto.ArchivoDTO;
import com.utn.tesis.service.ArchivoService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/file")
@RequestScoped
public class FileAPI {
    @Inject
    private ArchivoService archivoService;

    @GET
    @Path("/image")
    @Produces({"image/jpeg", "image/png", "image/bmp"})
    public Response getImage(@QueryParam("id") Long id) {
        Response.ResponseBuilder response = Response.noContent();
        if (id != null) {
            try {
                ArchivoDTO archivo = archivoService.findArchivo(id);
                response = Response.ok(archivoService.findFile(id));
                response.header("Content-Disposition", String.format("attachment; filename=\"%s\"", archivo.getNombre()));
            } catch (IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return response.build();
    }
}
