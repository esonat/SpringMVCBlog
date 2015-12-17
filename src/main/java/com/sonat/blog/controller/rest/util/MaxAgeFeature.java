package com.sonat.blog.controller.rest.util;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class MaxAgeFeature implements DynamicFeature{
	
	public void configure(ResourceInfo ri,FeatureContext ctx){
		MaxAge max=ri.getResourceMethod().getAnnotation(MaxAge.class);
		if(max==null) return;
		
		CacheControlFilter filter=new CacheControlFilter(max.value());
		ctx.register(filter);
	}
}
