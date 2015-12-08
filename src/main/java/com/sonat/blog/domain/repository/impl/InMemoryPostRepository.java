package com.sonat.blog.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.util.ParallelSorter;
import org.springframework.stereotype.Repository;

import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.repository.PostRepository;

@Repository
public class InMemoryPostRepository implements PostRepository{
	List<Post> postList=new ArrayList<Post>(); 
	private int nextPostId;
	
	public InMemoryPostRepository(){
		Post post1=new Post("post1",1);
		post1.setID(1);
		Post post2=new Post("post2",2);
		post2.setID(2);
		Post post3=new Post("post3",1);
		post3.setID(3);
		Post post4=new Post("post4",2);
		post4.setID(4);
		Post post5=new Post("post5",1);
		post5.setID(5);
		
		postList.add(post1);
		postList.add(post2);
		postList.add(post3);
		postList.add(post4);
		postList.add(post5);
		
		nextPostId=6;
	}
	
	public List<Post> getAll() {
		return postList;
	}

	public Post getPostById(int ID) {
		Post postById=null;
		
		for(Post post:postList){
			if(ID==post.getID()) postById=post;
		}
		return postById;
	}

	public List<Post> getPostsByUserID(int userID) {
		List<Post> list=new ArrayList<Post>();
		for(Post post:postList){
			if(userID==post.getUserID()) list.add(post);
		}
		return list;
	}

	public void addPost(Post post) {
		post.setID(getNextUserId());
		postList.add(post);
	}

	public void deletePost(int ID) {
		int index=0;
		
		for(int i=0;i<postList.size();i++){
			if(ID==postList.get(i).getID()) index=i;
		}
		postList.remove(index);
	}
	private synchronized int getNextUserId(){
		return nextPostId++;
	}

}
