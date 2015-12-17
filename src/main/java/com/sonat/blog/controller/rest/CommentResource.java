//package com.sonat.blog.controller.rest;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.Response;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.springframework.stereotype.Controller;
//
//import com.sonat.blog.domain.Post;
//
//@Controller
//@Resource
//@Path("/rest")
//public class CommentResource {
//	
//	@GET
//	@Path("/post")
//	@Produces("application/json")
//	public String getAllPosts(){
//		List<Post> allPosts=postService.getAll();
//		
//		if(allPosts==null){
//			throw new WebApplicationException(Response.Status.NOT_FOUND);
//		}
//		 ObjectMapper mapper = new ObjectMapper();
//		 String result;
//		 try{
//			result=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allPosts);	 
//		 }catch(Exception e){
//			 throw new WebApplicationException(Response.Status.FORBIDDEN);
//		 }
//	 return result;
//	}
//}
