//package com.sonat.blog.service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.sonat.blog.domain.Category;
//import com.sonat.blog.domain.Comment;
//import com.sonat.blog.domain.Post;
//import com.sonat.blog.domain.User;
//import com.sonat.blog.domain.UserRole;
//
//
//public class PostServiceTests {
//	
//	@Autowired
//	private PostService postService;
//		
//	private List<Post> postList;
//	private List<User> userList;
//	private Category category;
//	private Post post;
//	
//	@Before
//	public void init(){
//		postList=new ArrayList<Post>();
//		
//		category=new Category("Java");
//		category.setID(1);
//		
//		UserRole adminRole	=new UserRole();
//		adminRole.setUserRoleId(1);
//		adminRole.setRole("ROLE_ADMIN");
//		
//		UserRole userRole	=new UserRole();
//		userRole.setUserRoleId(2);
//		userRole.setRole("ROLE_USER");
//				
//		Set<UserRole> userRoles=new HashSet<UserRole>();
//		userRoles.add(adminRole);
//		
//		User 	 user		=	new User("engin","engin","sonat",true,userRoles);
//		post		= 	new Post(1,"POST",new Date(),category,null,user);
//		Post post_2		= 	new Post(2,"POST",new Date(),category,null,user);
//		
//		Comment comment=new Comment("COMMENT", new Date(), null);
//		comment.setPost(post);
//		
//		post.getComments().add(comment);
//		
//	}
//	@Test
//	public void getPost(){
//		postService.addPost(post,category);
//		
////		assertEquals(, actual);
//	}
//	
//}
