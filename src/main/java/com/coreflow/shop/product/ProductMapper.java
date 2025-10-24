package com.coreflow.shop.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.ProductDTO;
import com.coreflow.shop.common.utils.SearchCriteria;

public interface ProductMapper {

	List<ProductDTO> getProductList(@Param("cri") SearchCriteria cri, @Param("cate_code") Integer cate_code);
	
	int getProductListCount(Integer cate_code);
	
	List<Map<String, Object>> getRecommendList();
	
	ProductDTO pro_detail(Integer pro_num);
	
	void review_count_update(Integer pro_num);
	
	// 상품후기 댓글 개수
	int getProductCountByPro_num(Integer pro_num);
}
