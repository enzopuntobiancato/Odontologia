package com.utn.tesis.api.commons;

import com.utn.tesis.api.ObjectMapperProvider;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPORuntimeException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.Bajeable;
import com.utn.tesis.model.EntityBase;
import com.utn.tesis.model.FileExtension;
import com.utn.tesis.service.BaseService;
import com.utn.tesis.util.MappingUtil;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * User: Enzo
 * Date: 15/02/15
 * Time: 17:07
 */
@Slf4j
public abstract class BaseAPI<T extends EntityBase> {

    public static final String FILE = "file";
    public static final String NAME = "name";
    public static final String EXTENSION = "extension";

    public abstract BaseService<T> getEjbInstance();

    protected static Class returningView;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(T entity) {
        try {
            if (entity.isNew()) {
                entity = getEjbInstance().create(entity);
            } else {
                getEjbInstance().update(entity);
            }
            if (returningView != null) {
                entity = (T) MappingUtil.serializeWithView(entity, returningView);
            }
            return Response.ok(entity).build();
        } catch (SAPOException se) {
            return persistenceRequest(se);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(T entity) {
        try {
            entity = getEjbInstance().remove(entity.getId(), ((Bajeable) entity).getMotivoBaja());
        } catch (SAPOException se) {
            return persistenceRequest(se);
        }
        return Response.ok().build();
    }

    @Path("/restore")
    @PUT
    public void restore(@QueryParam("id") Long id) {
        getEjbInstance().restore(id);
    }

    @Path("/findById")
    @GET
    public T findById(@QueryParam("id") Long id) {
        return getEjbInstance().findById(id);
    }

    @Path("/findAll")
    @GET
    public List<T> findAll() {
        return getEjbInstance().findAll();
    }

    public Response persistenceRequest(Exception e) {
        Response.ResponseBuilder builder;

        Exception origin = e instanceof SAPOException ? ((SAPOException) e).getException() : e;
        if (origin instanceof ConstraintViolationException) {
            builder = createViolationResponse(((ConstraintViolationException) origin).getConstraintViolations());
        } else if (origin instanceof SAPOValidationException) {
            Map<String, String> responseObj = ((SAPOValidationException) origin).getErrors();
            builder = Response.status(new SapoFailedRequestResponse().getStatusCode()).entity(responseObj);
        } else {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", origin.getMessage());
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj);
        }

        return builder.build();
    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation fields, and their message. This can then be used
     * by clients to show violations.
     *
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.error("Validation completed. Violations found: {}", violations.size());
        Map<String, String> responseObj = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return Response.status(new SapoFailedRequestResponse().getStatusCode()).entity(responseObj);
    }

    private Map<String, Object> loadFileMap(InputPart inputPart) {
        Map<String, Object> fileMap = new HashMap<String, Object>(3);
        fileMap.put(NAME, parseFileName(inputPart.getHeaders()));
        fileMap.put(EXTENSION, parseFileExtension(inputPart.getMediaType().toString()));
        try {
            fileMap.put(FILE, inputPart.getBody(InputStream.class, null));
        } catch (IOException e) {
            throw new SAPORuntimeException("An error happened");
        }
        return fileMap;
    }

    public Map<String, Object> retrieveFile(Map<String, List<InputPart>> formParts, String key) {
        List<InputPart> filePart = formParts.get(key);
        if (filePart != null) {
            if (filePart.size() > 1) {
                throw new SAPORuntimeException("Only one file expected");
            }
            return loadFileMap(filePart.get(0));
        }
        return null;
    }

    public List<Map<String, Object>> retrieveFiles(Map<String, List<InputPart>> formParts, String key) {
        List<InputPart> filesParts = formParts.get(key);
        List<Map<String, Object>> filesMaps = new ArrayList<Map<String, Object>>(filesParts.size());
        for (InputPart inputPart : filesParts) {
            filesMaps.add(loadFileMap(inputPart));
        }
        return filesMaps;
    }

    private String parseFileName(MultivaluedMap<String, String> headers) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        for (String name : contentDispositionHeader) {
            if (name.trim().startsWith("filename")) {
                String[] tmp = name.split("=");
                return tmp[1].trim().replace("\"", "");
            }
        }
        return UUID.randomUUID().toString();
    }

    public Object retrieveObject(Map<String, List<InputPart>> formParts, String key, Class objectType) {
        List<InputPart> objects = formParts.get(key);
        if (objects.size() > 1) {
            throw new SAPORuntimeException("More than one object found");
        }
        Object result;
        try {
            InputPart object = objects.get(0);
            String json = object.getBody(String.class, null);
            ObjectMapperProvider omp = new ObjectMapperProvider();
            ObjectMapper om = omp.getContext(ObjectMapper.class);
            result = om.readValue(json, objectType);
        } catch (IOException e) {
            throw new SAPORuntimeException("An error happened");
        }
        return result;
    }

    public FileExtension parseFileExtension(String mimeType) {
        FileExtension[] values = FileExtension.values();
        for (FileExtension fe : values) {
            if (mimeType.equals(fe.getMimeType())) {
                return fe;
            }
        }
        return FileExtension.NONE;
    }
}
