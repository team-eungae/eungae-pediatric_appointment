package com.playdata.eungae.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoMemberDto {
	private Long kakaoMemberId;
	private String email;
	private String name;
	private String phoneNumber;
	private String birthDate;
}
