package com.coreflow.shop;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coreflow.shop.category.CategoryService;
import com.coreflow.shop.common.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
	
	private final CategoryService categoryService;
		
	// 기본페이지
	@GetMapping("/")
	public String home(Model model) {
		
		List<CategoryDTO> firstCategoryList = categoryService.getFirstCategoryList();
		model.addAttribute("firstCategoryList", firstCategoryList);
		
		return "index";
	}
	
	@GetMapping("/sub1")
	public String sub1() {
		
		return "sub1";
	}
	
	@GetMapping("/sub2")
	public String sub2() {
		
		return "sub2";
	}
	
}
