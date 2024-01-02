package com.playdata.eungae.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.service.MemberService;
import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

	private final MemberService memberService;

    @PostMapping("/signup")
    public String singUp(SignUpMemberRequestDto signUpMemberRequestDto) {
        memberService.singUp(signUpMemberRequestDto);
        return "redirect:/login";
    }

    //회원 정보 수정
    @PatchMapping("/my/profile")
    public MemberUpdateResponseDto updateMember(
            @RequestBody MemberUpdateRequestDto updateRequestDto
    ) {
        return memberService.updateMember(updateRequestDto);
    }

}
