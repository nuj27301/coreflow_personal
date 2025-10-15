package com.coreflow.shop.admin.review;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.coreflow.shop.common.dto.ReviewDTO;
import com.coreflow.shop.common.dto.ReviewReplyDTO;
import com.coreflow.shop.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminReviewService {

	private final AdminReviewMapper adminReviewMapper;
	
	public List<ReviewDTO> review_list(SearchCriteria cri, String rev_rate, String rev_content) {
		return adminReviewMapper.review_list(cri, rev_rate, rev_content);
	}
	
	public int review_count(SearchCriteria cri, String rev_rate, String rev_content) {
		return adminReviewMapper.review_count(cri, rev_rate, rev_content);
	}
	
	public Map<String, Object> review_info(Integer rev_code) {
		return adminReviewMapper.review_info(rev_code);
	}
	
	public void review_reply(ReviewReplyDTO dto) {
		adminReviewMapper.review_reply(dto);
	}
	
	public void reply_edit(ReviewReplyDTO dto) {
		adminReviewMapper.reply_edit(dto);
	}
	
	public void reply_del(Integer reply_id) {
		adminReviewMapper.reply_del(reply_id);
	}
}
