package com.sonat.blog.util.security;

import com.sonat.blog.domain.User;

public interface SecurityUtilInterface {
	User getCurrentUser();
	String getCurrentUsername();
	boolean isCurrentUserAdmin();
	
}
