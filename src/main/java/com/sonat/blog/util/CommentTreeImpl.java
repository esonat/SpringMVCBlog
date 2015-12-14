package com.sonat.blog.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.service.CommentService;

@Component
public class CommentTreeImpl implements CommentTree{
	
	@Autowired
	private CommentService commentService;


	public List<CommentStruct> getCommentList(){
		List<Comment> 		allComments	= commentService.getAllComments();
		List<CommentStruct> list		= new ArrayList<CommentStruct>();
				
		if(allComments==null) return null;
		
		for(Comment comment:allComments){
			CommentStruct struct	= new CommentStruct();
			List<Comment> children	= new ArrayList<Comment>();
			
			int commentID	=comment.getID();
			int postID		=commentService.getPostOfComment(commentID).getID();
			int parentID	=(comment.getParent()==null)?0:comment.getParent().getID();

			children.addAll(commentService.getChildComments(postID,commentID));
			
			struct.setComment(comment);
			struct.setPostID(postID);	
			struct.setParentID(parentID);
			struct.setChildren(children);
			
			list.add(struct);
		}		
		return list;
	}	
}
