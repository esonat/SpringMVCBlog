package com.sonat.blog.controller.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.transform.RootEntityResultTransformer;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sonat.blog.domain.Post;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.service.PostService;

@Resource
@Controller
@Path("/rest")
public class PostResource {
	@Autowired
	private PostService postService;
	
	private final String POST_NOT_FOUND		="Post not found";
	private final String CATEGORY_NOT_FOUND	="Category not found";
		
	@GET
	@Path("/post")
	@Produces("application/json")
	public String getAllPosts(){
		List<Post> allPosts=postService.getAll();
		
		if(allPosts==null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		 ObjectMapper mapper = new ObjectMapper();
		 String result;
		 try{
			result=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allPosts);	 
		 }catch(Exception e){
			 throw new WebApplicationException(Response.Status.FORBIDDEN);
		 }
	 return result;
	}
	
	@GET
	@Path("/post/{id}")
	@Produces("application/json")
	public String getPostById(@PathParam("id")int id){
		Post post;
		
		try{
			post=postService.getPostById(id);
		}
		catch(PostNotFoundException e){
			return POST_NOT_FOUND;	
		}
		
		if(post==null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		 ObjectMapper mapper = new ObjectMapper();
		 String result;
		 try{
			result=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(post);	 
		 }catch(Exception e){
			 throw new WebApplicationException(Response.Status.FORBIDDEN);
		 }
	 return result;
	}
	
	@GET
	@Path(value="/post/category/{categoryId}")
	@Produces("application/json")
	public String getPostsByCategory(@PathParam("categoryId")int categoryID){
		List<Post> posts=postService.getPostsByCategory(categoryID);
		
		ObjectMapper mapper = new ObjectMapper();
		String result;
		
		try{
			result=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(posts);	 
		 }catch(Exception e){
			 throw new WebApplicationException(Response.Status.FORBIDDEN);
		 }
		 return result;
	}
	
	@GET
	@Path("/post/user/{username}")
	@Produces("application/json")
	public String getPostsByUsername(@PathParam("username")String username){
		List<Post> posts=postService.getPostsByUsername(username);
		
		ObjectMapper mapper = new ObjectMapper();
		String result;
		
		try{
			result=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(posts);	 
		 }catch(Exception e){
			 throw new WebApplicationException(Response.Status.FORBIDDEN);
		 }
		 return result;
	}	
}
