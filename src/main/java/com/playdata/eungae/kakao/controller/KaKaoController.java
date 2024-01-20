package com.playdata.eungae.kakao.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class KaKaoController {

	KakaoApi kakaoApi = new KakaoApi();

	@RequestMapping("/auth/kakao")
	public ModelAndView kakaoLogin(
		@RequestParam("code") String code,
		HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();

		// 1. 인증 코드 요청 전달
		String accessToken = kakaoApi.getAccessToken(code);

		// 2. 인증코드로 토큰 전달
		HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

		log.info("userInfo : {}", userInfo);

		if (userInfo.get("email") != null) {
			session.setAttribute("userId", userInfo.get("email"));
			session.setAttribute("accessToke", accessToken);
		}

		modelAndView.addObject("userId", userInfo.get("email"));
		modelAndView.setViewName("index");
		return modelAndView;
	}
}
