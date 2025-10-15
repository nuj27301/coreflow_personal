package com.coreflow.shop.admin.review;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coreflow.shop.admin.login.AdminLoginVO;
import com.coreflow.shop.common.dto.ReviewDTO;
import com.coreflow.shop.common.dto.ReviewReplyDTO;
import com.coreflow.shop.common.utils.FileUtils;
import com.coreflow.shop.common.utils.PageMaker;
import com.coreflow.shop.common.utils.SearchCriteria;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/review/*")
public class AdminReviewController {

	private final AdminReviewService adminReviewService;
	
	@Value("${com.coreflow.upload.path}")
	private String uploadPath;
	
	// 상품리뷰목록
	@GetMapping("/review_list")
	public void review_list(@ModelAttribute("cri") SearchCriteria cri, @ModelAttribute("rev_rate") String rev_rate, @ModelAttribute("rev_content") String rev_content, Model model) throws Exception {
		
		List<ReviewDTO> review_list = adminReviewService.review_list(cri, rev_rate, rev_content);
		
		model.addAttribute("review_list", review_list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(adminReviewService.review_count(cri, rev_rate, rev_content));
		model.addAttribute("pageMaker", pageMaker);
	}
	
	// 답변하기버튼, 답변수정버튼
	@GetMapping("/review_info/{rev_code}")
	public ResponseEntity<Map<String, Object>> review_info(@PathVariable("rev_code") Integer rev_code) {
		ResponseEntity<Map<String, Object>> entity = null;
		
		entity = new ResponseEntity<Map<String,Object>>(adminReviewService.review_info(rev_code), HttpStatus.OK);
		
		return entity;
	}
	
	// 모달답변등록버튼
	@PostMapping(value = "/review_reply", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> review_reply(@RequestBody ReviewReplyDTO dto, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;
		
		AdminLoginVO adminLoginVO = (AdminLoginVO) session.getAttribute("admin_auth");
		dto.setManager_id(adminLoginVO.getAd_userid());
		
		adminReviewService.review_reply(dto);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 모달답변수정버튼
	@PostMapping("/reply_edit")
	public ResponseEntity<String> reply_edit(ReviewReplyDTO dto) throws Exception {
		ResponseEntity<String> entity = null;
		
		adminReviewService.reply_edit(dto);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 답변삭제버튼
	@DeleteMapping("/reply_del/{reply_id}")
	public ResponseEntity<String> reply_del(@PathVariable("reply_id") Integer reply_id) throws Exception {
		ResponseEntity<String> entity = null;
		
		log.info("아이디" + reply_id);
		
		adminReviewService.reply_del(reply_id);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}
}
