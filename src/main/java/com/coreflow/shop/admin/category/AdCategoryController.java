package com.coreflow.shop.admin.category;

import java.util.List;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coreflow.shop.common.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdCategoryController {
  
  private final AdCategoryService adCategoryService;

  //1차카테고리르 부모로하는 2차 카테고리 코드 
  //여기서 입력후 html 로 이동
  @GetMapping("/secondcategory/{cate_prtcode}") //// 이렇게 까지 하고 메서드 작업 후 xml에서 작업.   ajax요청  /admin/categories/secondcategory/1
  public ResponseEntity<List<CategoryDTO>> secondList(@PathVariable("cate_prtcode") Integer cate_prtcode) {
	  ResponseEntity<List<CategoryDTO>> entity = null;
		  
	  entity = new ResponseEntity<List<CategoryDTO>>(adCategoryService.secondList(cate_prtcode),HttpStatus.OK);
	   
	  return entity;
	  
	  }
	  
  }

