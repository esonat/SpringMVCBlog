package com.sonat.blog.controller.rest.filter;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.Provider;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Component;

@Provider
@PreMatching
@ValidateMethod
public class PostMethodFilter implements ContainerRequestFilter{
	
	public void filter(ContainerRequestContext ctx)
	{	
		String method=ctx.getMethod();
		if(method.equals("GET")
		|| method.equals("DELETE")){
			ctx.abortWith(null);
		}
	}
}
