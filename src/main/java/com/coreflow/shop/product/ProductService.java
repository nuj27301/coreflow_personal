package com.coreflow.shop.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.coreflow.shop.common.dto.ProductDTO;
import com.coreflow.shop.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductMapper productMapper;
	
	public List<ProductDTO> getProductList(SearchCriteria cri, Integer cate_code) {
		return productMapper.getProductList(cri, cate_code);
	}
	
	public int getProductListCount(Integer cate_code) {
		return productMapper.getProductListCount(cate_code);
	}
	
	public List<ProductDTO> getRecommendList() {
		return productMapper.getRecommendList();
	}
	
	public 	ProductDTO pro_detail(Integer pro_num) {
		return productMapper.pro_detail(pro_num);
	}
	
	public 	int getProductCountByPro_num(Integer pro_num) {
		return productMapper.getProductCountByPro_num(pro_num);
	}
}
