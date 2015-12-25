/*package com.sonat.blog.controller;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.ContentObject;
import com.sonat.blog.domain.Post;
import com.sonat.blog.service.SearchService;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping
	public String searchBlog(@RequestParam("keyword")String keyword,
							 Model model){
		
		List<? extends ContentObject> results=searchService.getResults(keyword);
		if(results==null) return "searchResults";
		
		List<Post> postList			=	new ArrayList<Post>();
		List<Comment> commentList	=	new ArrayList<Comment>();
		
		for(ContentObject item:results){
			if(item instanceof Post)	postList.add((Post)item); 
			if(item instanceof Comment) commentList.add((Comment)item); 
		}
		
		model.addAttribute("postList", postList);
		model.addAttribute("commentList", commentList);
		
		return "searchResults";
	}
}
*/