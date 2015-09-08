package com.utn.tesis.api.commons;

import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 29/08/15
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
public class SapoFailedRequestResponse implements Response.StatusType {
    @Override
    public int getStatusCode() {
        return 1000;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response.Status.Family getFamily() {
        return Response.Status.Family.INFORMATIONAL;
    }

    @Override
    public String getReasonPhrase() {
        return "Specific SAPO domain errors.";
    }
}
