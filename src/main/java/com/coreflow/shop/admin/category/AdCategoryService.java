package com.coreflow.shop.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coreflow.shop.common.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

/**
 * [카테고리 서비스 인터페이스]
 * - 서비스 계층의 규칙(메서드 시그니처)만 정의
 * - 실제 동작은 ServiceImpl 클래스에서 구현
 */ 

@RequiredArgsConstructor 
@Service
public class AdCategoryService {
    private final AdCategoryMapper adCategoryMapper;
   
   //  첫번째 카테고리    
    public List<CategoryDTO> getFirstList(){
    	return adCategoryMapper.firstList();
    }    
    
    
    //CATE_PRTCODE
    //두번째 카테고리 1차카테고리를 참조
    public List<CategoryDTO> secondList(Integer cate_prtcode){
    	return adCategoryMapper.getSecondList(cate_prtcode);
    }

    
    //상품수정 폼에서 사용할 성택한 1차 카테고리 리스트들
	public CategoryDTO getFirstCategoryBySecondCategory(int secondCategory) {
		
		return adCategoryMapper.getFirstCategoryBySecondCategory(secondCategory);
	}


	

	
    
}
