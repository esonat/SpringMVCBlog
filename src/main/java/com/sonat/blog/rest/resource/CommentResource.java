package com.sonat.blog.rest.resource;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.exception.CommentNotFoundException;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.service.CommentService;

@Controller
@Resource
@Path("/rest")
public class CommentResource {
	@Autowired
	private CommentService commentService;
	
	@GET
	@Path("/post/{postId}/comment/list")
	@Produces("application/json")
	public String listPostComments(@PathParam("postId")int postID){
		List<Comment> postComments;
		
		try{
			postComments=commentService.getPostComments(postID);
		}catch(PostNotFoundException e){
			return ErrorMessages.POST_NOT_FOUND;}
	
		 ObjectMapper mapper = new ObjectMapper();
		 String result;
		 try{
			result=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(postComments);	 
		 }catch(Exception e){
			 throw new WebApplicationException(Response.Status.FORBIDDEN);
		 }	 
		 return result;
	}
	
	@GET
	@Path("/post/{postId}/comment/{commentId}")
	@Produces("application/json")
	public String getPostComment(@PathParam("postId")int postID,
								 @PathParam("commentId")int commentID){
		Comment postComment;
		try{
			postComment	=	commentService.getPostCommentById(postID,commentID);
		}catch(PostNotFoundException e){
			return ErrorMessages.POST_NOT_FOUND;
		}catch(CommentNotFoundException e){
			return ErrorMessages.COMMENT_NOT_FOUND;
		}
		 
		ObjectMapper mapper = new ObjectMapper();
		String result;
		 try{
			result=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(postComment);	 
		 }catch(Exception e){
			 throw new WebApplicationException(Response.Status.FORBIDDEN);
		 }	 
		 return result;
	}
	
	@GET
	@Path("/post/{postId}/comment/{commentId}/comment/list")
	@Produces("application/json")
	public String listChildComments(@PathParam("postId")int postID,
									@PathParam("commentId")int commentID){
		List<Comment> childComments;
		try{
			childComments=commentService.getChildComments(postID,commentID);
		}catch(PostNotFoundException e){
			return ErrorMessages.POST_NOT_FOUND;
		}catch (CommentNotFoundException e) {
			return ErrorMessages.COMMENT_NOT_FOUND;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String result;
		 try{
			result=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(childComments);	 
		 }catch(Exception e){
			 throw new WebApplicationException(Response.Status.FORBIDDEN);
		 }	 
		 return result;
	}
	
	@GET
	@Path(value="/post/{postId}/comment/{commentId}/comment/{childCommentId}")
	@Produces("application/json")
	public String getChildComment(@PathParam("postId")int postID,
								  @PathParam("commentId")int commentID,
								  @PathParam("childCommentId")int childCommentID){
		
		Comment childComment;
		try{
			childComment=commentService.getChildCommentById(postID, commentID, childCommentID);
		}catch(PostNotFoundException e){
			return ErrorMessages.POST_NOT_FOUND;
		}catch(CommentNotFoundException e){
			return ErrorMessages.COMMENT_NOT_FOUND;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String result;
		 try{
			result=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(childComment);	 
		 }catch(Exception e){
			 throw new WebApplicationException(Response.Status.FORBIDDEN);
		 }	 
		 return result;
	}
}
