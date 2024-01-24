package com.playdata.eungae.kakao.dto;

import com.playdata.eungae.kakao.domain.OauthKakao;

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

	private String signUpYn;

	public static OauthKakao toEntity(OauthKakaoDto oauthKakaoDto, KakaoMemberDto kakaoMemberDto) {
		return OauthKakao.builder()
			.kakaoMemberId(kakaoMemberDto.getKakaoMemberId())
			.accessToken(oauthKakaoDto.getAccessToken())
			.expiresIn(oauthKakaoDto.getExpiresIn())
			.refreshToken(oauthKakaoDto.getRefreshToken())
			.refreshTokenExpiresIn(oauthKakaoDto.getRefreshTokenExpiresIn())
			.signUpYn("N")
			.build();
	}
}
