package com.sonat.blog.util;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.id.enhanced.SequenceStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;
import com.sonat.blog.service.impl.CommentServiceImpl;

@Component
public class CommentTreeImpl implements CommentTree{
	
	@Autowired
	private CommentService commentService;

	public List<CommentStruct> getCommentList(){
		List<Comment> allComments= commentService.getAllComments();
		List<CommentStruct> list=new ArrayList<CommentStruct>();
				
		if(allComments==null) return null;
		
		for(Comment comment:allComments){
			int commentID=comment.getID();
			int postID=commentService.getPostOfComment(commentID).getID();
			List<Comment> children=new ArrayList<Comment>();
			
			CommentStruct struct=new CommentStruct();
			struct.setComment(comment);
			struct.setPostID(postID);	
			
			if(comment.getParent()!=null)
				struct.setParentID(comment.getParent().getID());
			else struct.setParentID(0);
			
			children.addAll(commentService.getChildComments(postID,commentID));
			struct.setChildren(children);
			
			boolean exists=false;
			for(CommentStruct item:list){
				if(item.comment.getID()==struct.getComment().getID()) exists=true;
			}			
			if(!exists) list.add(struct);
		}		
		return list;
	}	
}
