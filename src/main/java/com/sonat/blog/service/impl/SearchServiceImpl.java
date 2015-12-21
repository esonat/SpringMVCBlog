package com.sonat.blog.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sonat.blog.domain.ContentObject;
import com.sonat.blog.domain.repository.CommentRepository;
import com.sonat.blog.domain.repository.PostRepository;
import com.sonat.blog.domain.repository.impl.PostRepositoryImpl;
import com.sonat.blog.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	public List<? extends ContentObject> getResults(String keyword){
		try {
			postRepository.doIndex();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return postRepository.searchPosts(keyword);
	}
}
