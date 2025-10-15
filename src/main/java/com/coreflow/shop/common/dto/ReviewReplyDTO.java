package com.coreflow.shop.common.dto;

import java.time.LocalDateTime;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ReviewReplyDTO {

	private Integer reply_id;
	private Integer rev_code;
	private String manager_id;
	private String reply_text;
	private LocalDateTime reply_date;
}
