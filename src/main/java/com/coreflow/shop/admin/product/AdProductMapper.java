package com.coreflow.shop.admin.product;

import java.util.List;

import com.coreflow.shop.common.dto.ProductDTO;
import com.coreflow.shop.common.utils.SearchCriteria;




public interface AdProductMapper {
  // dto,xml다음으로 입력  
	void pro_insert(ProductDTO dto);  
	
	List<ProductDTO> pro_list(SearchCriteria cri); 
	
	int getTotalCount(SearchCriteria cri);
	
	//상품수정 
	ProductDTO pro_edit_form(Integer pro_num);
	
	//저장
	void pro_edit_modify(ProductDTO dto);
	//삭제
	void pro_delete(Integer pro_num);
     
}


