package com.playdata.eungae.kakao.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class OauthKakao {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long oauthKakaoSeq;

	private Long kakaoMemberId;

	@Setter
	private String accessToken;

	@Setter
	private Integer expiresIn;

	@Setter
	private String refreshToken;

	@Setter
	private Integer refreshTokenExpiresIn;

	private String signUpYn;
}
