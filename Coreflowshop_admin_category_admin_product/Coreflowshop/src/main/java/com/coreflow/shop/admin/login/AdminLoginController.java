package com.coreflow.shop.admin.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/*")
@Controller
public class AdminLoginController {

	private final AdminLoginService adminLoginservice;
	private final PasswordEncoder passwordEncoder;
	
	// 관리자 로그인주소
	@GetMapping("/") //  /admin/
	public String ad_login() {
		
		log.info("관리자로그인");
		
		
		return "/admin/ad_login";
	}
	
	@GetMapping("/login")
	public void loginForm() {
		
	}
	
	@PostMapping("/admin_ok")
	public String admin_ok(AdminLoginVO vo, HttpSession session, RedirectAttributes rttr) throws Exception {
		

	    System.out.println("=========== 로그인 시도됨 ===========");
	    log.info("로그인 시도 아이디: {}", vo.getAd_userid());

	    // 🧩 추가: 비밀번호 암호화 테스트 (임시)
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String enc = encoder.encode("1234");
	    System.out.println("🔐 암호화된 비밀번호(1234): " + enc);

	 
		
		// 아이디를 사용하여 회원정보를 DB로부터 가져온다.
		// 아이디가 존재하면 dto객체안에서 아이디에 해당하는 회원정보가 저장된다. 
		// 아이디가 존재하지 않으면 dto객체는 null이 된다. 
		AdminLoginVO dto = adminLoginservice.login(vo.getAd_userid());
		
		String url = ""; // 로그인시 주소이동.
		String status = ""; // 로그인 성공, 실패에 따른 메세지용도.
		
		if(dto != null) {
			// 비밀번호 확인하는 작업. admin_tbl 비밀번호 암호화는 수동으로 되어있음
			if(passwordEncoder.matches(vo.getAd_passwd(), dto.getAd_passwd())) {

				dto.setAd_passwd(""); // 비밀번호 공백처리
				// 서버측의 메모리에 인증된 상태라는 의미의 정보를 세션형태로 저장한다. 
				session.setAttribute("admin_auth", dto); // 반드시! 유일한 명칭이여야 한다. 특히 login_auth 겹치지 않게. 
				
				url = "/admin/ad_menu"; // 로그인 성공후 주소 
				status = "success";
	
			}else {
				// 비밀번호가 잘못되었다.
				url = "/admin/"; // 비밀번호가 틀려서 다시 로그인 
				status = "pwFail";
			}
		}else {
			url = "/admin/"; // 아이디가 틀려서 다시 로그인 
			status = "idFail";
		}
		
		// 2번째 status변수의 내용으로 타임리프페이지에 status으로 사용하게 하는 구문.
		rttr.addFlashAttribute("status", status); // 리다이렉트 
		
	 return "redirect:" + url;
	}
	
	
	
	// 관리자 메뉴
	@GetMapping("/ad_menu")
	public String ad_min(){ 
	
	return "/admin/ad_menu";
	
	} 
	

	
}
