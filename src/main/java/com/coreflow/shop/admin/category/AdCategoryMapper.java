package com.coreflow.shop.admin.category;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coreflow.shop.common.dto.CategoryDTO;

//@Mapper
public interface AdCategoryMapper {
   // 1차 카테고리  
	List<CategoryDTO> firstList();
	
	//2차 카테고리 
    List<CategoryDTO> getSecondList(Integer cate_prtcode);  // secondList(Integer CATE_PRTCODE) xml의 secondList의 1차 카테고리를 참조
	
    //메서드 secondCategory를  부모롷 하는 것을 getFirstCategoryBySecondCategory로 찼는다 어디서 xml에서 
	CategoryDTO getFirstCategoryBySecondCategory(int secondCategory);

	
   
}




//findAll(): 메서드 테이블에 있는 모든 데이터를 SELECT 해서 리스트(List)로 가져오는 기능