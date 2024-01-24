package com.playdata.eungae.kakao.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.playdata.eungae.kakao.dto.KakaoMemberDto;
import com.playdata.eungae.kakao.dto.OauthKakaoDto;
import com.playdata.eungae.kakao.service.KakaoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class KakaoViewController {

	private final KakaoService kakaoService;

	@GetMapping("/login/oauth2/code/kakao")
	public String kakaoOauth(
		@RequestParam("code") String code,
		Model model) throws JsonProcessingException {

		OauthKakaoDto oauthKakaoDto = kakaoService.kakaoLogin(code);
		String signUpYn = oauthKakaoDto.getSignUpYn();
		KakaoMemberDto memberInfo = kakaoService.getKakaoMemberInfo(code);

		if (signUpYn.equals("N")) {
			model.addAttribute("memberInfo", memberInfo);
			log.info("****** memberInfo : {}",memberInfo);
			return "contents/member/sign-up";
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(memberInfo.getEmail(), "1234");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "index";
	}
}
