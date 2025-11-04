package com.coreflow.shop.kakaologin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.coreflow.shop.common.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoLoginService {
    
    @Value("${kakao.client_id}")
    private String clientId;
    
    @Value("${kakao.redirect_uri}")
    private String redirectUri;
    
    @Value("${kakao.client_secret}")
    private String clientSecret;
    
    private final KakaoLoginDao kakaoLoginDao;
    
    /**
     * 카카오 로그인 URL 생성
     */
    public String getKakaoLoginUrl() {
        String kakaoUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code";
        
        log.info("카카오 로그인 URL: {}", kakaoUrl);
        return kakaoUrl;
    }
    
    /**
     * 인가 코드로 액세스 토큰 발급
     */
    private String getAccessToken(String code) {
        String accessToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + clientId);
            sb.append("&client_secret=" + clientSecret);
            sb.append("&redirect_uri=" + redirectUri);
            sb.append("&code=" + code);
            
            bw.write(sb.toString());
            bw.flush();
            
            int responseCode = conn.getResponseCode();
            log.info("카카오 토큰 요청 응답코드: {}", responseCode);
            
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            
            log.info("카카오 토큰 응답: {}", result);
            
            if (responseCode == 200) {
                JSONParser parser = new JSONParser();
                JSONObject elem = (JSONObject) parser.parse(result);
                accessToken = elem.get("access_token").toString();
            } else {
                log.error("카카오 토큰 발급 실패. 응답: {}", result);
                throw new RuntimeException("카카오 인증에 실패했습니다. REST API 키와 Redirect URI를 확인해주세요.");
            }
            
            br.close();
            bw.close();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("카카오 토큰 발급 실패", e);
            throw new RuntimeException("카카오 로그인 중 오류가 발생했습니다.");
        }
        
        return accessToken;
    }
    
    /**
     * 액세스 토큰으로 카카오 사용자 정보 조회
     */
    private Map<String, Object> getUserInfo(String accessToken) {
        Map<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            
            int responseCode = conn.getResponseCode();
            log.info("카카오 사용자 정보 요청 응답코드: {}", responseCode);
            
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            
            log.info("카카오 사용자 정보 응답: {}", result);
            
            if (responseCode == 200) {
                JSONParser parser = new JSONParser();
                JSONObject elem = (JSONObject) parser.parse(result);
                
                JSONObject kakaoAccount = (JSONObject) elem.get("kakao_account");
                JSONObject profile = (JSONObject) kakaoAccount.get("profile");
                
                String id = elem.get("id").toString();
                String email = kakaoAccount.get("email") != null ? kakaoAccount.get("email").toString() : "";
                String nickname = profile.get("nickname").toString();
                
                userInfo.put("id", id);
                userInfo.put("email", email);
                userInfo.put("nickname", nickname);
            } else {
                log.error("카카오 사용자 정보 조회 실패. 응답: {}", result);
                throw new RuntimeException("카카오 사용자 정보 조회에 실패했습니다.");
            }
            
            br.close();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("카카오 사용자 정보 조회 실패", e);
            throw new RuntimeException("카카오 사용자 정보 조회 중 오류가 발생했습니다.");
        }
        
        return userInfo;
    }
    
    /**
     * 카카오 로그인 전체 프로세스 처리
     * @param code 카카오 인가 코드
     * @return 로그인된 회원 정보
     * @throws RuntimeException 이미 일반 회원으로 가입된 이메일인 경우
     */
    public MemberDTO processKakaoLogin(String code) {
        // 1. 액세스 토큰 발급
        String accessToken = getAccessToken(code);
        
        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("카카오 액세스 토큰 발급에 실패했습니다.");
        }
        
        log.info("카카오 액세스 토큰 발급 성공");
        
        // 2. 사용자 정보 조회
        Map<String, Object> userInfo = getUserInfo(accessToken);
        
        String kakaoId = (String) userInfo.get("id");
        String email = (String) userInfo.get("email");
        String nickname = (String) userInfo.get("nickname");
        
        if (kakaoId == null || kakaoId.isEmpty()) {
            throw new RuntimeException("카카오 사용자 정보를 가져올 수 없습니다.");
        }
        
        log.info("카카오 사용자 정보 조회 - ID: {}, Email: {}, Nickname: {}", kakaoId, email, nickname);
        
        // 3. 이메일로 기존 일반 회원 존재 여부 확인
        if (email != null && !email.isEmpty()) {
            MemberDTO existingMember = kakaoLoginDao.findByEmail(email);
            if (existingMember != null && "normal".equals(existingMember.getMbsp_login_type())) {
                // 이미 일반 회원으로 가입된 이메일
                log.warn("이미 일반 회원으로 가입된 이메일: {}", email);
                throw new RuntimeException("이미 일반 회원으로 가입된 이메일입니다. 일반 로그인을 이용해주세요.");
            }
        }
        
        // 4. 카카오 ID로 기존 카카오 회원 조회
        MemberDTO member = kakaoLoginDao.findBySnsId(kakaoId, "kakao");
        
        if (member == null) {
            // 5. 신규 카카오 회원 자동 가입
            member = createKakaoMember(kakaoId, email, nickname);
            kakaoLoginDao.insertKakaoMember(member);
            log.info("카카오 신규 회원 가입 완료: {}", member.getMbsp_id());
        } else {
            // 6. 기존 회원 마지막 로그인 시간 업데이트
            kakaoLoginDao.updateLastLogin(member.getMbsp_id());
            log.info("카카오 기존 회원 로그인: {}", member.getMbsp_id());
        }
        
        return member;
    }
    
    /**
     * 카카오 회원 DTO 생성
     */
    private MemberDTO createKakaoMember(String kakaoId, String email, String nickname) {
        MemberDTO member = new MemberDTO();
        member.setMbsp_id("kakao_" + kakaoId); // 카카오 ID를 사용한 고유 아이디 생성
        member.setMbsp_name(nickname);
        member.setMbsp_email(email);
        member.setMbsp_login_type("kakao");
        member.setMbsp_sns_id(kakaoId);
        
        return member;
    }
}