package com.playdata.eungae.member.controller;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;
import com.playdata.eungae.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/my")
@RequiredArgsConstructor
public class MemberViewController {

    private final MemberService memberService;

    @GetMapping("/records")
    public String medicalRecordList() {
        return "contents/member/medical-records";
    }

    @GetMapping("/records/1")
    public String medicalRecordsDetails() {
        return "contents/member/medical-records-details";
    }

    @GetMapping("/profile")
    public String myPage() {
        return "contents/member/my-page";
    }

    @GetMapping("/appointments")
    public String myReservationList() {
        return "contents/member/my-reservations";
    }

    @GetMapping("/reviews")
    public String myReviews() {
        return "contents/member/my-review";
    }

    @GetMapping("/hospitals")
    public String regularHospitals() {
        return "contents/member/regular-hospital";
    }

    @GetMapping("/children")
    public String myChildren() {
        return "contents/member/my-children";
    }

    @GetMapping("/children/form")
    public String addMyChildren() {
        return "contents/member/my-children-add";
    }

    @GetMapping("/profile/form")
    public String updateProfile(Model model) {
        model.addAttribute(memberService.findById(1L));
        return "contents/member/my-page-form";
    }

}
