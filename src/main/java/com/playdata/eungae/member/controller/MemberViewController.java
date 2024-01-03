package com.playdata.eungae.member.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/my")
public class MemberViewController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

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
	public String singUp(
		@Valid SignUpMemberRequestDto signUpMemberRequestDto,
		BindingResult bindingResult,
		Model model) {

		if (bindingResult.hasErrors()) {
			return "contents/member/login";
		}

		try {
			Member member = SignUpMemberRequestDto.toEntity(signUpMemberRequestDto);
			memberService.signUp(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "contents/member/login";
		}

		return "redirect:/login";
	}
}
