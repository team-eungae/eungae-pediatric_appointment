package com.playdata.eungae.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/hospital")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkHospitalInFavorites(
        @AuthenticationPrincipal UserDetails principal,
        @RequestParam("hospitalSeq") Long hospitalSeq
    ) {
        return memberService.checkHospitalInFavorites(hospitalSeq, principal.getUsername());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/profile/form")
    public String updateMemberInfo(@RequestBody @Valid MemberUpdateRequestDto updateRequestDto) {
        memberService.updateMemberInfo(updateRequestDto.getEmail(), updateRequestDto);
        return "successful";
    }

    @PatchMapping("/hospital")
    @ResponseStatus(HttpStatus.OK)
    public String changeFavorite(
        @AuthenticationPrincipal UserDetails principal,
        @RequestParam("hospitalSeq") Long hospitalSeq
    ) {
        memberService.changeFavoriteStatus(hospitalSeq, principal.getUsername());
        return "Favorites have been successfully changed";
    }
}
