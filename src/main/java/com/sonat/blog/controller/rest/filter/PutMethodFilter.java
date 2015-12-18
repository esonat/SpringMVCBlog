package com.sonat.blog.controller.rest.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

@Provider
@PreMatching
@ValidateMethod
public class PutMethodFilter implements ContainerRequestFilter{
	protected static final String AUTHORIZATION_PROPERTY = "Authorization";
    protected static final String AUTHENTICATION_SCHEME = "Basic";
    protected static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());;
    protected static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
    protected static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<Object>());;
	
	public void filter(ContainerRequestContext ctx)
	{	
		//ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker)ctx.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		///Method method = methodInvoker.getMethod();
        String method=ctx.getMethod();
		if(method.equals("PUT")){
			ctx.setMethod("GET");
		}
	}
}
