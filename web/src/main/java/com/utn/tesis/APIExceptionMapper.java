package com.utn.tesis;

import com.utn.tesis.api.commons.SapoFailedRequestResponse;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 7/05/16
 * Time: 16:48
 */
@Slf4j
@Provider
public class APIExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        return persistenceRequest(exception);
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
}
