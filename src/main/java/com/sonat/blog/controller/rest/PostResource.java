package com.sonat.blog.controller.rest;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sonat.blog.controller.rest.filter.PostMethodFilter;
import com.sonat.blog.controller.rest.filter.ValidateMethod;
import com.sonat.blog.controller.rest.util.MaxAge;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;
import com.sonat.blog.exception.CategoryNotFoundException;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.PostService;

@Resource
@Controller
@Path("/rest")
public class PostResource {
	@Autowired
	private PostService postService;
	@Autowired
	private CategoryService categoryService;
	
	@POST
	@Path("/post/add")
	@Consumes("application/json")
    public Response addPost(Post post,
    					   @QueryParam("categoryName")String categoryName){
		
		try{
			Category category=categoryService.getCategoryByName(categoryName);
	     	postService.addPost(post,category);
		}catch(Exception e){
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.CREATED).build();
	}
	

	@GET
	@Path("/post")
	@Produces("application/json")
	@ValidateMethod
	public String getAllPosts(){
		List<Post> allPosts=postService.getAll();
//		 if(allPosts==null){
//			//throw new WebApplicationException(Response.Status.NOT_FOUND);
//		}
		ContainerRequestContext requestContext=ResteasyProviderFactory.getContextData(ContainerRequestContext.class);
		 
		
		 ObjectMapper mapper = new ObjectMapper();
		 String result="ABC";
		 
			try {
				result=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allPosts);
			} catch (JsonGenerationException e) {
				return "Json Generation Exception";
			} catch (JsonMappingException e) {
				return "Json Mapping Exception";
			} catch (IOException e) {
				return "IO Exception";
			}	 
		 
	 return result;
	}
	
	@GET
	@Path("/post/{id}")
	@Produces("application/json")
	@MaxAge(500)
	public String getPostById(@PathParam("id")int id){
		Post post;
		
		try{
			post=postService.getPostById(id);
		}
		catch(PostNotFoundException e){
			return ErrorMessages.POST_NOT_FOUND;	
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
	@MaxAge(500)
	public String getPostsByCategory(@PathParam("categoryId")int categoryID){
		List<Post> posts;
		
		try{
			posts=postService.getPostsByCategory(categoryID);
		}
		catch(CategoryNotFoundException e){
			return ErrorMessages.CATEGORY_NOT_FOUND;	
		}
		
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
	@MaxAge(500)
	public String getPostsByUsername(@PathParam("username")String username){
		List<Post> posts;
		try{
			posts=postService.getPostsByUsername(username);
		}catch(UserNotFoundException e){
			return ErrorMessages.USER_NOT_FOUND;
		}
		
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
