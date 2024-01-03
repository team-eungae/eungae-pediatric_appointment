package com.playdata.eungae.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.member.dto.RequestFavoriesDto;
import com.playdata.eungae.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my")
public class MemberApiController {

	private final MemberService memberService;

	@PostMapping("/hospital")
	@ResponseStatus(HttpStatus.OK)
	public String appendFavorites(@Valid RequestFavoriesDto requestFavoriesDto) {
		memberService.appendFavorites(requestFavoriesDto);
		return "즐겨찾기 추가 성공";
	}

	@PatchMapping("/hospital")
	@ResponseStatus(HttpStatus.OK)
	public String removeFavorites(@Valid RequestFavoriesDto requestFavoriesDto) {
		memberService.removeFavorites(requestFavoriesDto);
		return "즐겨찾기 삭제 성공";
	}

}
