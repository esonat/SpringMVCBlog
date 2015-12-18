package com.sonat.blog.controller.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sonat.blog.domain.Post;

import junit.framework.Assert;

public class UserResourceTest {
	private static Client client;
	private final String POST_NOT_FOUND		="Post not found";
	private final String COMMENT_NOT_FOUND	="Comment not found";
	private final String CATEGORY_NOT_FOUND	="Category not found";
	public final String USER_NOT_FOUND="User not found";
	
	@BeforeClass
	public static void initClient(){
		client=ClientBuilder.newClient();
	}
	@AfterClass
	public static void closeClient(){
		client.close();
	}
	@Test
	public void testUserResource(){
		System.out.println("Get posts by username");
		Response response=client.target("http://localhost:8080/blog/rest/post/user/engin")
				.request()
				.get();
				
		String value=response.readEntity(String.class);
		Assert.assertFalse(value.equals(USER_NOT_FOUND));		
				
		ObjectMapper mapper = new ObjectMapper();
		List<Post> posts=new ArrayList<Post>();
		
		try {
			posts=mapper.readValue(value,new TypeReference<List<Post>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		Assert.assertEquals(1,posts.size());
		response.close();
		
		/*************************/
		System.out.println("Get posts by username");
		response=client.target("http://localhost:8080/blog/rest/post/user/a")
				.request()
				.get();
				
		value=response.readEntity(String.class);
		Assert.assertEquals(value,USER_NOT_FOUND);		
		response.close();
	}
}
