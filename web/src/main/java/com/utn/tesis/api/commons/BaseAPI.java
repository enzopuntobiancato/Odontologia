package com.utn.tesis.api.commons;

import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.EntityBase;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 15/02/15
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseAPI {

    public Response persistenceRequest(Exception e) {
        Response.ResponseBuilder builder = null;

        Exception origin = e instanceof SAPOException ? ((SAPOException)e).getException() : e;
        if (origin instanceof ConstraintViolationException) {
            builder = createViolationResponse(((ConstraintViolationException) origin).getConstraintViolations());
        } else if (origin instanceof SAPOValidationException) {
            Map<String, String> responseObj = ((SAPOValidationException)origin).getErrors();
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } else {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", origin.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
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

        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(responseObj);
    }
}
