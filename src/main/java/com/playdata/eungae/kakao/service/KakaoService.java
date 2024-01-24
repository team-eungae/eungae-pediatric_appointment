package com.playdata.eungae.kakao.service;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playdata.eungae.kakao.domain.OauthKakao;
import com.playdata.eungae.kakao.dto.KakaoMemberDto;
import com.playdata.eungae.kakao.dto.OauthKakaoDto;
import com.playdata.eungae.kakao.repository.OauthKakaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

	private final OauthKakaoRepository oauthKakaoRepository;

	public void kakaoMemberDto(String code) throws JsonProcessingException {

		// getKakaoMemberInfo(accessToken);
	}

	private KakaoMemberDto getKakaoMemberInfo(String accessToken) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HTTP 요청 보내기
		HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> response = rt.exchange(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.POST,
			kakaoUserInfoRequest,
			String.class
		);

		// responseBody에 있는 정보를 꺼냄
		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);

		Long id = jsonNode.get("id").asLong();
		String email = jsonNode.get("kakao_account").get("email").asText();
		String nickname = jsonNode.get("properties").get("nickname").asText();

		log.info("id : {}", id);
		log.info("email : {}", email);
		log.info("nickname : {}", nickname);
		return KakaoMemberDto.builder()
			.email(jsonNode.get("kakao_account").get("email").asText())
			.name(jsonNode.get("properties").get("nickname").asText())
			.birthYear(jsonNode.get("kakao_account").get("birthYear").asText())
			.birthDay(jsonNode.get("kakao_account").get("birthday").asText())
			.phoneNumber(jsonNode.get("kakao_account").get("phone_number").asText())
			.build();
	}

	// private String saveKakaoOAuth(Long kakaoMemberId) {
	// 	OauthKakao byKakaoMemberId = oauthKakaoRepository.findByKakaoMemberId(kakaoMemberId).get();
	//
	// 	if (byKakaoMemberId != null) {
	// 		byKakaoMemberId.setAccessToken();
	// 	}
	//
	// }

	private OauthKakaoDto getAccessToken(String code) throws JsonProcessingException {
		// HTTP Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HTTP Body 생성
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", "0180d0e4a5af2d2dc8bb4f5aa715e58d");
		body.add("redirect_uri", "http://localhost:8090/login/oauth2/code/kakao");
		body.add("code", code);

		// HTTP 요청 보내기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> response = rt.exchange(
			"https://kauth.kakao.com/oauth/token",
			HttpMethod.POST,
			kakaoTokenRequest,
			String.class
		);

		// HTTP 응답 (JSON) -> 액세스 토큰 파싱
		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		log.info("****** access_token : {}", jsonNode.get("access_token").asText());
		log.info("****** expires_in : {}", jsonNode.get("expires_in").asText());
		log.info("****** refresh_token : {}", jsonNode.get("refresh_token").asText());

		String accessToken = jsonNode.get("access_token").asText();

		// 카카오 고유번호를 받아오기. https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#get-token-info


		// 데이터베이스에 유저의 고유번호,엑세스토큰,만료시간,리프레시토큰,만료시간,회원가입 여부

		return OauthKakaoDto.builder()
			.accessToken(jsonNode.get("access_token").asText())
			.expiresIn(jsonNode.get("expires_in").asInt())
			.refreshToken(jsonNode.get("refresh_token").asText())
			.refreshTokenExpiresIn(jsonNode.get("refresh_token_expires_in").asInt())
			.build();
	}

}
