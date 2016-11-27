package com.utn.tesis;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 21/11/16
 * Time: 22:00
 * To change this template use File | Settings | File Templates.
 */
//@Provider
//@ServerInterceptor
public class ContentTypeSetterPreProcessorInterceptor implements
        PreProcessInterceptor {
    @Override
    public ServerResponse preProcess(HttpRequest httpRequest, ResourceMethod resourceMethod) throws Failure, WebApplicationException {
///*        httpRequest.setAttribute(InputPart.DEFAULT_CONTENT_TYPE_PROPERTY,
//                "**/*//*; charset=UTF-8");*/
        httpRequest.setAttribute("resteasy.provider.multipart.inputpart.defaultCharset", "UTF-8");
        return null;
    }
}
