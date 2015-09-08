package com.utn.tesis.api.commons;

import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.Bajeable;
import com.utn.tesis.model.EntityBase;
import com.utn.tesis.service.BaseService;
import com.utn.tesis.util.MappingUtil;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 15/02/15
 * Time: 17:07
 */
public abstract class BaseAPI<T extends EntityBase> {

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
            Logger.getLogger(BaseAPI.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(T entity) {
        try {
            entity = getEjbInstance().remove(entity.getId(), ((Bajeable)entity).getMotivoBaja());
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
        Response.ResponseBuilder builder = null;

        Exception origin = e instanceof SAPOException ? ((SAPOException)e).getException() : e;
        if (origin instanceof ConstraintViolationException) {
            builder = createViolationResponse(((ConstraintViolationException) origin).getConstraintViolations());
        } else if (origin instanceof SAPOValidationException) {
            Map<String, String> responseObj = ((SAPOValidationException)origin).getErrors();
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

        Logger.getLogger("Error").log(Level.SEVERE, "Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(new SapoFailedRequestResponse().getStatusCode()).entity(responseObj);
    }

}
