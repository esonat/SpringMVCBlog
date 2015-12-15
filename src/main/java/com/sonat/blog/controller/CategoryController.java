package com.sonat.blog.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sonat.blog.domain.Category;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.util.SecurityUtil;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/category/add",method=RequestMethod.GET)
	public String getAddCategoryForm(@ModelAttribute("category")Category category,
							   Model model,
							   BindingResult result){
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("categories",categories);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		return "addCategory";		
	}
	@RequestMapping(value="/category/add",method=RequestMethod.POST)
	public String addCategory(@ModelAttribute("category")@Valid Category category,
							   Model model,
							   BindingResult result){
		if(result.hasErrors()) return "addCategory";

		categoryService.addCategory(category);
		return "redirect:/post";		
	}
	@RequestMapping(value = "/category/{categoryId}/delete", method = RequestMethod.POST)
	public String deletePost(@PathVariable("categoryId") int categoryId){
		
		categoryService.deleteCategory(categoryId);
		return "redirect:/post";		
	}
}
