package com.coreflow.shop.kakaologin;

import com.coreflow.shop.common.dto.MemberDTO;

public interface KakaoLoginDao {
//	 로그인 id 확인
	public KakaoLoginDto kakaoOne(KakaoLoginDto dto);
	
	// 회원등록
	public int kakaoInsert(KakaoLoginDto dto);
	
	// 이메일로 회원 조회
	public MemberDTO findMemberByEmail(String email);  
	
	// MemberDTO로 회원 등록
    public int insertKakaoMember(MemberDTO member);    
	
}
