package com.playdata.eungae.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberApiController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public String singUp(SignUpMemberRequestDto signUpMemberRequestDto) {
		memberService.singUp(signUpMemberRequestDto);
		return "redirect:/login";
	}

}
