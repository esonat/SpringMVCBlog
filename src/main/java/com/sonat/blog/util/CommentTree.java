package com.sonat.blog.util;

import java.util.List;
import com.sonat.blog.domain.Comment;

public interface CommentTree {
	public void addToList(List<Comment> list);
}
