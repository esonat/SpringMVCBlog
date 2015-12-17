package com.sonat.blog.controller.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.hamcrest.core.Is;
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
		System.out.println("Get Post ID:1");
		Response response=client.target("http://localhost:8080/blog/rest/post/1")
				.request()
				.get();
		
		Assert.assertFalse(response.equals(POST_NOT_FOUND));
				
		System.out.println("Get Post ID:3");
		response=client.target("http://localhost:8080/blog/rest/post/1")
				.request()
				.get();
		
		Assert.assertEquals(response,POST_NOT_FOUND);
		
		
	}
}
