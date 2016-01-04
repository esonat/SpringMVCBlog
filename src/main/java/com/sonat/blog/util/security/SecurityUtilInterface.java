package com.sonat.blog.util.security;

import com.sonat.blog.domain.BlogUser;

public interface SecurityUtilInterface {
	BlogUser getCurrentUser();
	String getCurrentUsername();
	boolean isCurrentUserAdmin();
	
}
