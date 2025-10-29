package com.coreflow.shop.kakaologin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.coreflow.shop.common.dto.MemberDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class KakaoLoginService {
	
	
	@Value("${kakao.token_url}")
	private String token_url;
	
	@Value("${kakao.user.logout}")
	private String kakaologout;
	
	@Autowired
	KakaoLoginDao dao;
	
	// 토큰요청
	public String getAccessTokenFromKakao(String client_id, String code, String redirect_uri, String client_secret) throws IOException {
        //------kakao POST 요청------
        String reqURL = token_url + "?grant_type=authorization_code&client_id="+client_id+"&code="+code+"&redirect_uri="+redirect_uri+"&client_secret=" + client_secret + "&prompt=login";
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {});

        //System.out.println("Response Body : " + result);

        String accessToken = (String) jsonMap.get("access_token");
        //String refreshToken = (String) jsonMap.get("refresh_token");
        //String scope = (String) jsonMap.get("scope");

        return accessToken;
    }
	
	// 사용자정보조회
	public KakaoLoginDto getUserInfo(String access_Token, KakaoLoginDto dto) throws IOException {
        //------kakao GET 요청------
        String reqURL = "https://kapi.kakao.com/v2/user/me"; // 카카오서버로부터 개인정보를 참조하는 주소.
        
        URL url = new URL(reqURL);
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + access_Token);

        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);

        // 입력스트림 작업.
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        // 카카오 서버로부터 개인정보를 받아옴.
        System.out.println("Response Body : " + result);

        // jackson objectmapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // JSON String -> Map
        Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
        });

        //사용자 정보 추출
        Map<String, Object> properties = (Map<String, Object>) jsonMap.get("properties");
        Map<String, Object> kakao_account = (Map<String, Object>) jsonMap.get("kakao_account");

     // ID 추출
        Long id = (Long) jsonMap.get("id");
        dto.setId(id);

        // kakao_account에서 정보 추출 (null 체크)
        String name = "카카오사용자";  // 기본값
        String email = null;
        String nickname = null;
        String profileImage = null;

        if (kakao_account != null) {
            // 이름 추출 (있는 경우)
            if (kakao_account.get("name") != null) {
                name = kakao_account.get("name").toString();
            }
            
            // 이메일 추출
            if (kakao_account.get("email") != null) {
                email = kakao_account.get("email").toString();
                dto.setEmail(email);
            }

            // profile 객체에서 nickname 추출
            Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");
            if (profile != null) {
                if (profile.get("nickname") != null) {
                    nickname = profile.get("nickname").toString();
                }
                if (profile.get("profile_image_url") != null) {
                    profileImage = profile.get("profile_image_url").toString();
                }
            }
        }

        // properties에서 정보 추출 (profile에 없는 경우 대비)
        if (properties != null) {
            if (nickname == null && properties.get("nickname") != null) {
                nickname = properties.get("nickname").toString();
            }
            if (profileImage == null && properties.get("profile_image") != null) {
                profileImage = properties.get("profile_image").toString();
            }
        }

        // 최종 이름 결정: nickname이 있으면 사용, 없으면 name 사용
        if (nickname != null && !nickname.trim().isEmpty()) {
            dto.setName(nickname);
        } else if (name != null && !name.trim().isEmpty()) {
            dto.setName(name);
        } else {
            dto.setName("카카오사용자");
        }

        // 프로필 이미지 설정 (필요시 주석 해제)
        // if (profileImage != null) {
        //     dto.setProfile_image(profileImage);
        // }

        br.close();

        return dto;
    }	
	
//	 로그인 id 확인
	public KakaoLoginDto kakaoOne(KakaoLoginDto dto) {
		return dao.kakaoOne(dto);
	};
	
	// 카카오 로그인 확인
    public MemberDTO findMemberByEmail(String email) {
        return dao.findMemberByEmail(email);
    }
    
    public void insertKakaoMember(MemberDTO member) {
        dao.insertKakaoMember(member);
    }
	
	// 회원등록
	public int kakaoInsert(KakaoLoginDto dto) {
		return dao.kakaoInsert(dto);
	};
	
	// 카카오 로그아웃.  https://kauth.kakao.com/oauth/logout.  헤더는 있고, 파라미터는 없는 경우.
	// 헤더 Authorization: Bearer ${ACCESS_TOKEN}
	public void kakaologout(String accessToken) throws JsonProcessingException {
		
		// Http Header 생성.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded");
		
		// Http 요청작업.
		HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);
		
		// Http 요청하기
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(kakaologout, HttpMethod.POST, kakaoLogoutRequest, String.class);
		
		//리턴된 정보 : JSON포맷의 문자열.
		String responseBody = response.getBody();
		log.info("responseBody:" + responseBody);
		
		// JSON문자열을 Java객체로 역직렬화 하거나 Java객체를 JSON으로 직렬화 할 때 사용하는 Jackson라이브러리의 클래스이다.
		// ObjectMapper 생성 비용이 비싸기때문에 bena/static 으로 처리하는 것이 성능에 좋다.
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		
		Long id = jsonNode.get("id").asLong();
		
		log.info("id:" + id); // 로그아웃 이후 카카오에서 응답한 카카오회원번호
		
	}
	
}
