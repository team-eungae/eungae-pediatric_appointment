package com.playdata.eungae.member.controller;


import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;
import org.springframework.web.bind.annotation.*;

import com.playdata.eungae.member.service.MemberService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RequestMapping("/my")
@RestController
public class MemberApiController {

	private final MemberService memberService;

	@PatchMapping("/profile/form/{memberSeq}")
	public MemberUpdateResponseDto updateMemberInfo(@PathVariable Long memberSeq, 
							@RequestBody MemberUpdateRequestDto updateRequestDto) {
		return memberService.updateMemberInfo(memberSeq, updateRequestDto);
	}

}
