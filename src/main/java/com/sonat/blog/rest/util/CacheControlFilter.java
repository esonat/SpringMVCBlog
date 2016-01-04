package com.sonat.blog.rest.util;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.CacheControl;

public class CacheControlFilter implements ContainerResponseFilter{
	private int maxAge;
	
	public CacheControlFilter(int maxAge){
		this.maxAge=maxAge;
	}
	
	public void filter(ContainerRequestContext req,ContainerResponseContext res)
	throws IOException{
		if(req.getMethod().equals("GET")){
			CacheControl cc=new CacheControl();
			cc.setMaxAge(this.maxAge);
			res.getHeaders().add("Cache-Control",cc);
		}
	}
}
