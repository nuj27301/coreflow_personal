package com.coreflow.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;


@Controller
public class HomeController {

		
	// 기본페이지
	@GetMapping("/")
	public String home() {
		
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
