package com.sonat.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.repository.CommentRepository;
import com.sonat.blog.domain.repository.PostRepository;
import com.sonat.blog.service.PostService;
import com.sonat.blog.service.UserService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentRepository commentRepository;
	
	public List<Post> getAll() {
		return postRepository.getAll();
	}

	public Post getPostById(int ID) {
		return postRepository.getPostById(ID);
	}

	public List<Post> getPostsByUsername(String username) {
		return postRepository.getPostsByUsername(username);
	}

	public void addPost(Post post) {
		postRepository.addPost(post);
	}

	public void deletePost(int ID) {
		postRepository.deletePost(ID);
	}
	
/** COMMENTS **/
}
