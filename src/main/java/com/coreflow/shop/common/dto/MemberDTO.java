package com.coreflow.shop.common.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
	
	private String mbsp_id;
	private String mbsp_name;
	private String mbsp_email;
	private String mbsp_password;
	private String mbsp_zipcode;
	private String mbsp_address;
	private String mbsp_addressdetail;
	private String mbsp_phone;
	private String mbsp_gender;
	private String mbsp_receive;
	private String mbsp_birthdate;
	private int mbsp_point;
	private Date mbsp_lastlogin;
	private Date mbsp_datesub;
	private Date mbsp_updatedate;
	
	// 카카오 로그인 추가 필드
    private String mbsp_login_type;  // normal, kakao
    private String mbsp_sns_id;       // 카카오 고유 ID
	
}
