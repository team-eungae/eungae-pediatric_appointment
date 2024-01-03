package com.playdata.eungae.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.service.MemberService;

import lombok.RequiredArgsConstructor;

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
	public String updateProfile() {
		return "contents/member/my-page-form";
	}

	@PostMapping("/signup")
	public String singUp(SignUpMemberRequestDto signUpMemberRequestDto) {
		memberService.singUp(signUpMemberRequestDto);
		return "redirect:/login";
	}
}
