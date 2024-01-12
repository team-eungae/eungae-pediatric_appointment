package com.playdata.eungae.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.RequestFavoriesDto;
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

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/profile/form")
    public String updateMemberInfo(@RequestBody @Valid MemberUpdateRequestDto updateRequestDto) {
        memberService.updateMemberInfo(updateRequestDto.getEmail(), updateRequestDto);
        return "successful";
    }

    @PostMapping("/hospital")
    @ResponseStatus(HttpStatus.CREATED)
    public String appendFavorites(@RequestBody @Valid RequestFavoriesDto requestFavoriesDto) {
        memberService.appendFavorites(requestFavoriesDto);
        return "Favorites have been successfully appended";
    }

    @PatchMapping("/hospital")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String removeFavorites(@RequestBody @Valid RequestFavoriesDto requestFavoriesDto) {
        memberService.removeFavorites(requestFavoriesDto);
        return "Favorites have been successfully deleted";
    }
}
