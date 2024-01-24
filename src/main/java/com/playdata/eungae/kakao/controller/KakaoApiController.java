package com.playdata.eungae.kakao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.playdata.eungae.kakao.service.KakaoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class KakaoApiController {

	private final KakaoService kakaoService;

	@GetMapping("/login/oauth2/code/kakao")
	public String kakaoOauth(@RequestParam("code") String code) throws JsonProcessingException {
		log.info(code);
		kakaoService.kakaoMemberDto(code);

		return "okay";
	}
}
