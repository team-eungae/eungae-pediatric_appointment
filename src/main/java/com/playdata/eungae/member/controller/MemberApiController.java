package com.playdata.eungae.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.RequestFavoriesDto;
import com.playdata.eungae.member.dto.ResponseFavoritesHospitalDto;
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

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/profile/form")
    public String updateMemberInfo(@RequestBody @Valid MemberUpdateRequestDto updateRequestDto) {
        memberService.updateMemberInfo(updateRequestDto.getEmail(), updateRequestDto);
        return "successful";
    }

    @GetMapping("/hospital")
    @ResponseStatus(HttpStatus.OK)
    public String findFavorites(
        Model model,
        @AuthenticationPrincipal UserDetails principal
    ) {
        List<ResponseFavoritesHospitalDto> responseFavoritesHospitalDtos = memberService.findFavorites(principal.getUsername());
        return "Favorites have been successfully appended";
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
