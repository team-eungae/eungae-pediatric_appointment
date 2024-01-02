package com.playdata.eungae.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.playdata.eungae.member.dto.RequestFavoriesDto;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.service.MemberService;

import jakarta.validation.Valid;
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

	@ResponseBody
	@PostMapping("/api/my/hospital")
	public ResponseEntity<?> appendFavorites(@Valid RequestFavoriesDto requestFavoriesDto) {
		memberService.appendFavorites(requestFavoriesDto);
		return new ResponseEntity<String>("즐겨찾기 추가 성공", HttpStatus.OK);
	}

}
