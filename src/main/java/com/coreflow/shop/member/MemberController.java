package com.coreflow.shop.member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coreflow.shop.common.dto.EmailDTO;
import com.coreflow.shop.common.dto.LoginDTO;
import com.coreflow.shop.common.dto.MemberDTO;
import com.coreflow.shop.kakaologin.KakaoLoginDto;
import com.coreflow.shop.kakaologin.KakaoLoginService;
import com.coreflow.shop.mail.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/member/*")
@Slf4j
@Controller
public class MemberController {
	
	private final EmailService emailService;
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final KakaoLoginService kservice; 
	
	@Value("${kakao.authorize}")
	private String authorize;
	
	@Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;
    
    @Value("${kakao.client_secret}")
    private String client_secret;
	
	@GetMapping("/join")
	public void join() {
		
	}
	
	// 아이디 중복체크
	@GetMapping("/idCheck")
	public ResponseEntity<String> idCheck(String mbsp_id) throws Exception {
		ResponseEntity<String> entity = null;
		
		log.info("아이디 : " + mbsp_id);
	
		String isUse = "";
		
		if(memberService.idCheck(mbsp_id) !=null) {
			isUse = "no"; // 아이디 사용불가
		}else {
			isUse = "yes"; // 아이디 사용가능
		}
		
		entity = new ResponseEntity<String>(isUse, HttpStatus.OK); 
		// 아이디가 회원테이블에 존재하는지 여부 체크 
				
		return entity;
	}
	
	
	// 회원정보저장
	@PostMapping("/join")
	public String join(MemberDTO dto) {
		
		// passwordEncoder.encode(dto.getMbsp_password()) : 비밀번호 암호화작업
		String enc_password = passwordEncoder.encode(dto.getMbsp_password());
		dto.setMbsp_password(enc_password);
		
		memberService.join(dto);
		
		return "redirect:/member/login";
	}
	
	// 로그인 
	/*
	 * @GetMapping("/login") public void loginForm() {
	 * 
	 * }
	 */
	
	@GetMapping("/login")
	public void loginForm(Model model) {
		  String location = authorize + "?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri + "&prompt=login";
	      model.addAttribute("location", location);
	}
	
	@PostMapping("/login")
	public String loginProcess(LoginDTO dto, HttpSession session, RedirectAttributes rttr) throws Exception{
		
		// 아이디를 사용하여 회원정보를 DB로부터 가져온다. 
		// 아이디가 존재하면 vo객체안에서 아이디에 해당하는 회원정보가 저장된다. 
		// 아이디가 존재하지 않으면 vo객체는 null이 된다.
		MemberDTO vo = memberService.login(dto.getMbsp_id());
		
		String url = ""; //로그인시 주소이동
		String status = ""; // 로그인 성공, 실패에 따른 메세지 용도.
		
		if(vo != null) {
			if(passwordEncoder.matches(dto.getMbsp_password(), vo.getMbsp_password())) {
				//vo.setMbsp_password("");
				// 서버측의 메모리에 인증된 상태라는 의미의 정보를 세션형태로 저장한다. 
				session.setAttribute("login_auth", vo);
				url = "/";
				status = "success";
								
			}else {
				// 비밀번호가 잘못 되었다. 
				url = "/member/login";
				status = "pwFail";
			}			
		}else {
			url = "/member/login"; // 아이디가 틀려서 다시 로그인 주소로 이동 
			status = "idFail";
		}
		
		rttr.addFlashAttribute("status", status); // 리다이렉트되는 주소의 페이지에서 사용목적
						
		return "redirect:" + url; // redirect가 없으면 url을 주소로 해석하고, redirect가 있으니 타임리프로 해석한다. 
	}
//	// 로그아웃
//	@GetMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate(); // 세션으로 관리되는 서버쪽의 모든 메모리소멸.
//		
//		return "redirect:/"; // 메인주소로 이동
//	}
	
	// 카카오 로그인 API서버에서 호출받게되는 주소.
		// 카카오 디벨로퍼 사이트에서 로그인작업을 위하여 미리 설정해둠.
	@GetMapping(value="/kakao/callback")
	public String loginKakaoRedirect(KakaoLoginDto dto, MemberDTO mdto, Model model, HttpSession session) throws Exception {

	    System.out.println("토큰요청인가코드: "+dto.getCode());

	    // 인증토큰 받기
	    String accessToken = kservice.getAccessTokenFromKakao(client_id, dto.getCode(), redirect_uri, client_secret);

	    // 카카오서버로부터 개인회원정보 받기
	    dto = kservice.getUserInfo(accessToken, dto);

	    log.info("KakaoLoginDto: " + dto);

	    // 1. 카카오 이메일로 기존 회원 조회
	    MemberDTO member = kservice.findMemberByEmail(dto.getEmail());
	    
	    // 2. 회원이 없으면 신규 가입 처리
	    if(member == null) {
	        member = new MemberDTO();
	        
	        // KakaoLoginDto의 정보를 MemberDTO로 변환
	        member.setMbsp_id("kakao_" + dto.getId());  // dto.getId() 사용
	        member.setMbsp_email(dto.getEmail());       // dto.getEmail() 사용
	        member.setMbsp_name(dto.getName());         // dto.getName() 사용
	        member.setMbsp_password("KAKAO_LOGIN");
	        member.setMbsp_point(0);
	        member.setMbsp_receive("Y");
	        
	        // 전화번호가 있으면 설정
	        if(dto.getPhone() != null && !dto.getPhone().isEmpty()) {
	            member.setMbsp_phone(dto.getPhone());
	        }
	        
	        // DB에 저장
	        kservice.insertKakaoMember(member);
	        
	        // 저장 후 다시 조회
	        member = kservice.findMemberByEmail(dto.getEmail());
	    }
	    
	    // 3. 세션에 저장 (일반 로그인과 동일한 키 사용!)
	    session.setAttribute("login_auth", member);
	    session.setAttribute("kakao_login_auth", dto.getEmail());
	    session.setAttribute("accessToken", accessToken);

	    log.info("카카오 로그인 성공 - 회원ID: " + member.getMbsp_id());

	    return "redirect:/";
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) throws JsonProcessingException {
		
	
		String kakao_login_auth =  (String) session.getAttribute("kakao_login_auth");
		
		if(kakao_login_auth != null && !"".equals(kakao_login_auth)) {
			String accessToken =  (String) session.getAttribute("accessToken");
			
			// 카카오 로그아웃 API 호출
			kservice.kakaologout(accessToken);
			
			// 카카오 로그인 인증정보를 세션으로 소멸
			session.removeAttribute("kakao_login_auth");
			session.removeAttribute("accessToken");

		}
		// 현재 프로젝트에서 일반로그인상태를 로그아웃 처리.
		session.invalidate();
		
		
		return "redirect:/";  // 메인주소로 이동.
	}
	
	// 회원정보수정 폼. select문 회훤정보를 읽어오기. 
	@GetMapping("/modify")
	public String modify(HttpSession session, Model model) throws Exception {
		
		String url = "";
		MemberDTO dto = (MemberDTO) session.getAttribute("login_auth");
		
		if(dto != null) {
			
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		
		MemberDTO vo = memberService.modify(mbsp_id);
		
		model.addAttribute("memberDTO", vo);
		
			url = "/member/modify";
		} else {
			url = "redirect:/member/login";
		}
				
		return url;
	}
	
	// 회원정보 수정하기
	@PostMapping("/modify")
	public String modify(MemberDTO dto, RedirectAttributes rttr) throws Exception {
		
		memberService.modify_save(dto);
		
		rttr.addFlashAttribute("status", "modify");
		
		return "redirect:/";
	}
	
	// 아이디 및 비밀번호 찾기 폼
	@GetMapping("/lostpass")
	public String lostpass() throws Exception {
		
		return "member/lostpass";
	}
		
	// 아이디 찾기 - 메일발송
	@GetMapping("/idsearch")
	public ResponseEntity<String> idsearch(String mbsp_name, String mbsp_email) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		String result = "";
		
		String mbsp_id = memberService.idsearch(mbsp_name, mbsp_email);
		
		if(mbsp_id != null) {
			
			// 아이디 메일발송
			String type = "mail/idsearch";
			
			EmailDTO dto = new EmailDTO();
			dto.setReceiverMail(mbsp_email); // 받는사람 메일주소
			dto.setSubject("CoreFlowShop 아이디 찾기 결과를 보냅니다.");
			
			result = "success";
			emailService.sendMail(type, dto, mbsp_id);			
		} else {
			result = "fail";
		}
		
		entity = new ResponseEntity<String>(result,HttpStatus.OK);
		
		return entity;
	}
	
	// 임시비밀번호 발급 - 메일발송. ajax요청
	@GetMapping("/pwtemp")
	public ResponseEntity<String> pwtemp(String mbsp_id, String mbsp_email) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		String result = "";
		
		// 아이디와 전자우편이 존재하는 지 DB에서 체크 
		String d_u_email = memberService.pwtemp_comfirm(mbsp_id, mbsp_email);
		
		if(d_u_email != null) {
			result = "success";
			
			// 임시비밀번호 암호화하여, DB에 저장
			String imsi_pw = emailService.createAuthCode();
			
			// imsi_pw 암호화
			memberService.pwchange(mbsp_id, passwordEncoder.encode(imsi_pw));
			
			// 임시비밀번호(imsi_pw) 메일발송
			String type = "mail/pwtemp";
					
			EmailDTO dto = new EmailDTO();
			dto.setReceiverMail(d_u_email);
			dto.setSubject("CoreFlowShop 임시비밀번호를 보냅니다.");
			
			emailService.sendMail(type, dto, imsi_pw);
		
		}else {
			result = "fail";
		}
		
				
		entity = new ResponseEntity<String>(result, HttpStatus.OK);
		
		return entity;
	}
	
	
	// 마이페이지
	@GetMapping("/mypage")
	public void mypage() throws Exception {
		
	}
	
	// 비밀번호 변경하기 폼 
	@GetMapping("/pwchange") // /member/pwchange.html
	public void pwchange() throws Exception {
		
	}
	
	// 비밀번호 변경하기
	@PostMapping("/pwchange")
	public ResponseEntity<String> pwchange(@RequestParam("cur_pw") String mbsp_password, String new_pw, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;
		
		 // RedirectAttributes rttr 기능?
		 /*
		 1)주소를 리다이렉트 할때 파라미터를 이용하여, 쿼리스트링을 주소에 추가 할수가 있다.
		 2)리다이렉트 되는 주소의 타임리프 페이지에서 자바스크립트로 참조하고자 할 경우.  
		 */
		
		String url = "";
		String msg = "";
		
		// 암호화된 비밀번호
		String db_mbsp_password = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_password();
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		String mbsp_email = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_email();
		
		// 현재 사용중인 비밀번호를 세션으로 저장되어있던 암호화된 비밀번호를 가져와서, 사용중인 비밀번호로 암호화가 된 것인지 여부를 판단. 
		
		if(passwordEncoder.matches(mbsp_password, db_mbsp_password)) {
			// 1) 신규비밀번호를 암호화 한다. (60바이트로 암호화)
			String encode_new_pw = passwordEncoder.encode(new_pw);
			
			// 2) 암호화한 비밀번호를 변경한다. 
			memberService.pwchange(mbsp_id, encode_new_pw);
			
			// 비밀번호 변경알림 메일발송
			String type = "mail/pwchange";
			
			EmailDTO dto = new EmailDTO();
			dto.setReceiverMail(mbsp_email);
			dto.setSubject("CoreFlowShop 비밀번호 변경알림을 보냅니다.");
			
			emailService.sendMail(type, dto, new_pw);
			
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
			
			// log.info("테스트1");
						
		}else {
			entity = new ResponseEntity<String>("fail", HttpStatus.OK);
			// log.info("테스트2");
		}
		
					
		return entity;
	}
	
	// 회원탈퇴 
	@GetMapping("/withdrawal") // /member/withdrawal.html
	public void withdrawal() throws Exception {
		
	}
	
	// 회원탈퇴 
	@PostMapping("/withdrawal")
	public ResponseEntity<String> withdrawal(HttpSession session) throws Exception {
		
			
		ResponseEntity<String> entity = null;
		
		String mbsp_id = ((MemberDTO) session.getAttribute("login_auth")).getMbsp_id();
		
		session.invalidate();
		
		memberService.withdrawal(mbsp_id);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
		
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
