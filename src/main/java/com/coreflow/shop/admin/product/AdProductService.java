package com.coreflow.shop.admin.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.coreflow.shop.common.dto.ProductDTO;
import com.coreflow.shop.common.utils.SearchCriteria;

import java.util.List;

/**
 * ★역할: 상품 관련 비즈니스 로직 구현
 * - Controller ↔ Service ↔ Mapper(DAO) 구조에서 중간 계층
 */
@Service                         // ★ 스프링의 서비스 컴포넌트 등록
@RequiredArgsConstructor         // ★ final 필드 생성자 자동 생성
public class AdProductService {

    private final AdProductMapper adProductMapper; // ★ Mapper 주입 (DB 접근 담당) 
    
    //서비스매퍼 자바의 ProductDTO 받음
    public void pro_insert(ProductDTO dto) {
    	adProductMapper.pro_insert(dto);
    }
    //상품목록조회
    public List<ProductDTO> pro_list(SearchCriteria cri){
    	return adProductMapper.pro_list(cri);
    } 
    
    public int getTotalCount(SearchCriteria cri) {
    	return adProductMapper.getTotalCount(cri);
    } 
    // 상품수정
    public ProductDTO pro_edit_form(Integer pro_num) {
         
    	return adProductMapper.pro_edit_form(pro_num);
    }
    
    //상품수정저장 productDTO의 데이터를 db에 반영
    public void pro_edit_modify(ProductDTO dto) {
    	adProductMapper.pro_edit_modify(dto);
    }
    // 단일 상품삭제 
    public void pro_delete(Integer pro_num) {
    	adProductMapper.pro_delete(pro_num);
    }
}
