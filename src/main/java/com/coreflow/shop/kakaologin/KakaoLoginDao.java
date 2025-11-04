package com.coreflow.shop.kakaologin;

import org.apache.ibatis.annotations.Param;

import com.coreflow.shop.common.dto.MemberDTO;

public interface KakaoLoginDao {
	
	public MemberDTO findBySnsId(@Param("mbsp_sns_id") String snsId, @Param("mbsp_login_type") String loginType);
	
	public MemberDTO findByEmail(String email);
	
	public void insertKakaoMember(MemberDTO dto);
	
	public void updateLastLogin(String mbsp_id);
	
}
