package com.sonat.blog.controller.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class PostResourceTest {
	private static Client client;
	private final String POST_NOT_FOUND="Post not found";
	private final String COMMENT_NOT_FOUND="Comment not found";
	private final String CATEGORY_NOT_FOUND="Category not found";
	
	@BeforeClass
	public static void initClient(){
		client=ClientBuilder.newClient();
	}
	@AfterClass
	public static void closeClient(){
		client.close();
	}
	@Test
	public void testPostResource() throws Exception{
		System.out.println("Get Post ID:2");
		Response response=client.target("http://localhost:8080/blog/rest/post/2")
				.request()
				.get();
		String value=response.readEntity(String.class);
		Assert.assertFalse(value.equals(POST_NOT_FOUND));		
		response.close();
		
		System.out.println("Get Post ID:5");
		response=client.target("http://localhost:8080/blog/rest/post/5")
				.request()
				.get();
		
		value=response.readEntity(String.class);
		Assert.assertEquals(value,POST_NOT_FOUND);
		response.close();
	}
}
