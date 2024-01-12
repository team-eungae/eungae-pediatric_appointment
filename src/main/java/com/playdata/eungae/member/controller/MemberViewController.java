package com.playdata.eungae.member.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.appointment.dto.ResponseAppointmentDto;
import com.playdata.eungae.appointment.dto.ResponseDetailMedicalHistoryDto;
import com.playdata.eungae.appointment.dto.ResponseMedicalHistoryDto;
import com.playdata.eungae.appointment.service.AppointmentService;
import com.playdata.eungae.member.dto.MemberFindResponseDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;
import com.playdata.eungae.member.service.MemberService;
import com.playdata.eungae.review.dto.ResponseReviewDto;
import com.playdata.eungae.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/my")
@Controller
public class MemberViewController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AppointmentService appointmentService;
    private final ReviewService reviewService;

    @GetMapping("/records")
    public String medicalRecordList(
        Model model,
        @AuthenticationPrincipal UserDetails principal
        // @PathVariable int page,
    ) {
        List<ResponseMedicalHistoryDto> myMedicalRecords = appointmentService.getMyMedicalRecords(principal.getUsername());

        model.addAttribute("myMedicalRecords", myMedicalRecords);
        return "contents/member/medical-records";
    }

    @GetMapping("/records/{appointmentSeq}")
    public String medicalRecordsDetails(
        Model model,
        @PathVariable Long appointmentSeq
    ) {
        ResponseDetailMedicalHistoryDto myMedicalRecordDetail = appointmentService.getMyMedicalRecordDetail(appointmentSeq);
        model.addAttribute("myMedicalRecordDetail", myMedicalRecordDetail);
        return "contents/member/medical-records-details";
    }


    @GetMapping("/profile")
    public String myPage(@AuthenticationPrincipal UserDetails principal, Model model) {
        MemberFindResponseDto memberFindResponseDto = memberService.findMemberByEmail(principal.getUsername());
        model.addAttribute("member", memberFindResponseDto);
        return "contents/member/my-page";
    }

    @GetMapping("/profile/form")
    public String updateProfile(@AuthenticationPrincipal UserDetails principal, Model model) {
        MemberUpdateResponseDto MemberUpdateResponseDto = memberService.updateMemberByEmail(principal.getUsername());
        model.addAttribute("member", MemberUpdateResponseDto);
        return "contents/member/my-page-form";
    }

    @GetMapping("/reviews")
    public String myReviewList(
        @AuthenticationPrincipal UserDetails principal,
        Model model
    ) {
        List<ResponseReviewDto> responseReviewDtos = reviewService.findReviewsByMemberEmail(principal.getUsername());
        model.addAttribute("responseReviewDtos", responseReviewDtos);
        return "contents/member/my-review";
    }

    @GetMapping("/appointments")
    public String myReservationList(
        @AuthenticationPrincipal UserDetails principal,
        Model model
    ) {
        List<ResponseAppointmentDto> responseAppointmentDtos = appointmentService.findAllAppointment(principal.getUsername());
        model.addAttribute("responseAppointmentDtos", responseAppointmentDtos);
        return "contents/member/my-reservations";
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
}
