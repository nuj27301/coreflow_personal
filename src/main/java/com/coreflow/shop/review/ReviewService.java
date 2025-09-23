package com.coreflow.shop.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.coreflow.shop.common.dto.ReviewDTO;
import com.coreflow.shop.common.utils.PageMaker;
import com.coreflow.shop.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	
	private final ReviewMapper reviewMapper;
	
	public Map<String, Object> getReviewList( Integer pro_num, int page) {
		Map<String, Object> map = new HashMap<>();
		
		SearchCriteria cri = new SearchCriteria();
		cri.setPage(page);
		
		List<ReviewDTO> rev_list = reviewMapper.rev_list(pro_num, cri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(reviewMapper.getReviewCountByPro_num(pro_num));
		
		map.put("rev_list", rev_list);
		map.put("pageMaker", pageMaker);
		
		
		return map;
	}
	

}
