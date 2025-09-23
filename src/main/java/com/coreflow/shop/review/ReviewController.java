package com.coreflow.shop.review;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review/*")
@RestController // 타임리프페이지 사용안함. 상품상세페이지 일부를 사용한다. ajax
public class ReviewController {
	
	private final ReviewService reviewService;
	
	// /review/rev_list/1/1
	@GetMapping("/rev_list/{pro_num}/{page}")
	public ResponseEntity<Map<String, Object>> rev_list(@PathVariable("pro_num") Integer pro_num, @PathVariable("page") int page) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
		
		Map<String, Object> reviewList = reviewService.getReviewList(pro_num, page);
		
		entity = new ResponseEntity<Map<String,Object>> (reviewList, HttpStatus.OK);
		
		return entity;
	}
}
