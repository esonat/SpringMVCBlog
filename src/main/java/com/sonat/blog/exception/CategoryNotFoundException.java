package com.sonat.blog.exception;

public class CategoryNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6471891658089766858L;
	private int categoryID;
	
	public CategoryNotFoundException(int categoryID){
		this.categoryID=categoryID;
	}
	public int getCategoryID(){
		return categoryID;
	}
}
