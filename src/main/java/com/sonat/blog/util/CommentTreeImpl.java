package com.sonat.blog.util;

import java.security.KeyStore.PrivateKeyEntry;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

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
				
		for(Comment comment:allComments){
			CommentStruct struct=new CommentStruct();
			struct.setParent(comment);
			
			int parentID=comment.getID();
			struct.setPostID(commentService.getPostOfComment(comment.getID()).getID());
			struct.setChildren(commentService.getChildComments(parentID,comment.getID()));
			
			list.add(struct);
		}
		return list;
	}	
}
