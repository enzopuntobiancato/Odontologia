package com.utn.tesis;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 21/11/16
 * Time: 21:02
 * To change this template use File | Settings | File Templates.
 */
//@Provider
public class CharsetPreProcessInterceptor implements ContainerRequestFilter{

    @Context
    private HttpServletRequest servletRequest;
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        servletRequest.setAttribute(InputPart.DEFAULT_CONTENT_TYPE_PROPERTY, "text/plain; charset=UTF-8");
        System.out.println("ACA ESTOY");
    }
}
