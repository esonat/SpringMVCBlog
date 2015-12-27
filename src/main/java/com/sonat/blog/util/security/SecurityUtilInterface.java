package com.sonat.blog.util.security;

import com.sonat.blog.domain.User;

public interface SecurityUtilInterface {
	public User getCurrentUser();
	public String getCurrentUsername();
	public boolean isCurrentUserAdmin();
	
}
