package com.sonat.blog.domain;

import java.util.List;

public class User {
	private int ID;
	private String Name;
	
	public User() {
		super();
	}
	public User(String name) {
		this.Name=name;
	}	
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
}
