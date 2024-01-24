package com.playdata.eungae.kakao.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OauthKakaoDto {
	private Long kakaoMemberId;

	private String accessToken;

	private Integer expiresIn;

	private String refreshToken;

	private Integer refreshTokenExpiresIn;
}
