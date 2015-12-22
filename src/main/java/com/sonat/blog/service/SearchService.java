package com.sonat.blog.service;

import java.util.List;

import com.sonat.blog.domain.ContentObject;

public interface SearchService {
	List<? extends ContentObject> getResults(String keyword);
}
