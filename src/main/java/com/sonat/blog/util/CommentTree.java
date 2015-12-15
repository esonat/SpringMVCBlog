package com.sonat.blog.util;

import java.util.List;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public interface CommentTree {
	//List<CommentStruct> getCommentList(List<Post> postList);	
	public void addToList(List<Comment> list);
}
