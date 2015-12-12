package com.sonat.blog.util;

import java.util.List;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public class CommentStruct{
	public int postID;
	public Comment parent;
	public List<Comment> children; 
};
