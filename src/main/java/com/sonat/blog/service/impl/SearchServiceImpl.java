package com.sonat.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.search.SearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.sonat.blog.domain.ContentObject;
import com.sonat.blog.domain.repository.CommentRepository;
import com.sonat.blog.domain.repository.PostRepository;
import com.sonat.blog.domain.repository.impl.PostRepositoryImpl;
import com.sonat.blog.service.SearchService;

import ch.qos.logback.core.Context;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	public List<? extends ContentObject> getResults(String keyword){
		List<ContentObject> results=new ArrayList<ContentObject>();

		try {
			postRepository.doIndex();
		} catch (InterruptedException e) {
			return null;
		}
		
		try{
			results.addAll(postRepository.searchPosts(keyword));
			results.addAll(commentRepository.searchComments(keyword));	
		}catch(SearchException e){
			return null;
		}		
		return results;
	}
}
