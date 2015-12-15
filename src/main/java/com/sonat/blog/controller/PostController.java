package com.sonat.blog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.param.PositionalParameterSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;
import com.sonat.blog.util.CommentStruct;
import com.sonat.blog.util.CommentTree;
import com.sonat.blog.util.CommentTreeImpl;
import com.sonat.blog.util.SecurityUtil;

@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentService commentService;

//	@Autowired
//	private CommentTree commentTree;
//	
	@RequestMapping
	public String getAllPosts(Model model,
							  @ModelAttribute("comment") Comment comment){
		
		List<Post> postList=postService.getAll();
		Map<Post,List<Comment>> postsMap=new HashMap<Post, List<Comment>>();
		
		for(Post post:postList){
			if(!postsMap.containsKey(post)) postsMap.put(post,getCommentTree(post));
		}
		
		List<Category> categories=categoryService.getAllCategories();
	
		model.addAttribute("categories",categories);	
		model.addAttribute("postsMap",postsMap);
		//model.addAttribute("commentList",commentList);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		
		return "posts";
	}
	@RequestMapping("/{id}")
	public String getPostById(Model model,
							 @PathVariable("id")int id){
		Post post=postService.getPostById(id);
		
		if(post==null) return "redirect:/post";
		
		String username=post.getUser().getName();

		
		model.addAttribute("post",post);
		model.addAttribute("username",username);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		
		return "post";
	}		
	
	@RequestMapping(value="/category/{categoryId}")
	public String getPostsByCategory(Model model,
									 @ModelAttribute("comment") Comment comment,
									 @PathVariable("categoryId")int categoryId){
		List<Post> postList=postService.getPostsByCategory(categoryId);
		CommentTreeImpl commentTree=new CommentTreeImpl(postList);
		List<Comment> commentList=commentTree.getCommentList();
				
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("categories",categories);	
		model.addAttribute("postList",postList);
		model.addAttribute("commentList",commentList);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		
		return "posts";
	}
	
	
	@RequestMapping("/user/{username}")
	public String getPostsByUsername(Model model,
									 @PathVariable("username")String username){
		
		List<Post> list=postService.getPostsByUsername(username); 
				
		model.addAttribute("post",list);
		model.addAttribute("user",username);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		return "user";
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
    public String getAddPostForm(@ModelAttribute("post") Post post,
    							 Model model,
    							 BindingResult result){
		
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("categories",categories);
	    model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
	    return "addPost";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    public String addPost(@ModelAttribute("post")@Valid Post post,
    					  Model model,
    					  @RequestParam(value="categoryName")String categoryName,
    					  BindingResult result){
        if(result.hasErrors()) return "addPost";
               	
        Category category=categoryService.getCategoryByName(categoryName);
    	postService.addPost(post,category);
        return "redirect:/post"; 
    }   
	
		
	@RequestMapping(value = "/{postId}/delete", method = RequestMethod.POST)
	public String deletePost(final RedirectAttributes redirectAttributes,
							 @PathVariable("postId") int postId){
		postService.deletePost(postId);
		return "redirect:/post";		
	}

	public List<Comment> getCommentTree(Post post){
		List<Comment> visited=new ArrayList<Comment>();
		int postID=post.getID();
		
		for(Comment comment:commentService.getPostComments(postID)){
			//int commentID	= comment.getID();
			int depth		= comment.getDepth();
			
			findNext(comment,depth+1,visited);
		}
		return visited;
	}	  
	
	public void findNext(Comment comment,int depth,List<Comment> list){
		if(comment.getChildren()!=null
		&& comment.getChildren().size()>0){
			int commentID=comment.getID();
			List<Comment> children=commentService.getChildCommentsByDepth(commentID, depth);
			
			list.add(comment);
			for(Comment child:children) findNext(child,depth+1, list);
		}
	}
}
