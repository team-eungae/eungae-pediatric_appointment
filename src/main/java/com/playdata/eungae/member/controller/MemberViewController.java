package com.playdata.eungae.member.controller;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.MemberFindResponseDto;
import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;
import com.playdata.eungae.member.service.MemberService;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.appointment.dto.AppointmentResponseDto;
import com.playdata.eungae.appointment.service.AppointmentService;
import com.playdata.eungae.member.dto.MemberFindResponseDto;
import com.playdata.eungae.member.service.MemberService;

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
	@GetMapping("/records/{memberSeq}")
	public String medicalRecordList(Model model, @PathVariable("memberSeq") long memberSeq) {
		List<AppointmentResponseDto> myMedicalRecords = appointmentService.getMyMedicalRecords(memberSeq);
		model.addAttribute("myMedicalRecords", myMedicalRecords);
		return "contents/member/medical-records";
	}

    @GetMapping("/records/1")
    public String medicalRecordsDetails() {
        return "contents/member/medical-records-details";
    }

    @GetMapping("/profile/{memberSeq}")
    public String myPage(@PathVariable Long memberSeq, Model model) {
        MemberFindResponseDto memberFindResponseDto = memberService.findByMemberId(memberSeq);
        model.addAttribute("member", memberFindResponseDto);
        return "contents/member/my-page";
    }

    @GetMapping("/profile/form/{memberSeq}")
    public String updateProfile(@PathVariable Long memberSeq, Model model) {
        model.addAttribute("member", memberService.updateFindByMemberId(memberSeq));
        return "contents/member/my-page-form";
    }

	@GetMapping("/appointments")
	public String myReservationList() {
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
