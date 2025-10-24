package com.coreflow.shop.common.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.coreflow.shop.common.dto.MemberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		boolean result = false;
		
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("login_auth");
		
		if(memberDTO == null) {
			result = false;
			
			// ajax요청인지 구분
			if(isAjaxRequest(request)) {
				
				String originalUrl = request.getRequestURI();
				String postData = getPostData(request);
				
				session.setAttribute("targetUrl", originalUrl);
				session.setAttribute("postData", postData);
				
				// JSON 응답 반환
				sendJsonResponse(response, "로그인이 필요한 서비스입니다.", false);
				
			} else {
				getTargetUrl(request);
				
				session.setAttribute("loginMessage", "로그인이 필요한 서비스입니다.");
				
				response.sendRedirect("/member/login");
			}
		} else {
			result = true;
		}
		
		return result;
	}
	
	// JSON 응답을 보내는 메서드
	private void sendJsonResponse(HttpServletResponse response, String message, boolean success) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Map<String, Object> result = new HashMap<>();
		result.put("success", success);
		result.put("message", message);
		result.put("needLogin", true);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonResponse = mapper.writeValueAsString(result);
		
		PrintWriter out = response.getWriter();
		out.print(jsonResponse);
		out.flush();
	}
	
	private String getPostData(HttpServletRequest request) throws IOException {
		StringBuilder postData = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while((line = reader.readLine()) != null) {
			postData.append(line);
		}
		return postData.toString();
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("AJAX");
		return header != null && header.equals("true");
	}
	
	private void getTargetUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		
		if(query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		
		String targetUrl = uri + query;
		
		if(request.getMethod().equals("GET")) {
			request.getSession().setAttribute("targetUrl", targetUrl);
		}
	}
}