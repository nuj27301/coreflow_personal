package com.coreflow.shop.admin.review;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.ReviewDTO;
import com.coreflow.shop.common.dto.ReviewReplyDTO;
import com.coreflow.shop.common.utils.SearchCriteria;

public interface AdminReviewMapper {
	List<ReviewDTO> review_list(@Param("cri") SearchCriteria cri, @Param("rev_rate") String rev_rate, @Param("rev_content") String rev_content);
	
	int review_count(@Param("cri") SearchCriteria cri, @Param("rev_rate") String rev_rate, @Param("rev_content") String rev_content);
	
	Map<String, Object> review_info(Integer rev_code);
	
	void review_reply(ReviewReplyDTO dto);
	
	void reply_edit(ReviewReplyDTO dto);
	
	void reply_del(Integer reply_id);
}
