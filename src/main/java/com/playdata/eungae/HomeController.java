package com.playdata.eungae;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class HomeController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/signup")
	public String signUpForm(Model model) {
		model.addAttribute("signUpRequestDto", new SignUpMemberRequestDto());
		return "contents/member/sign-up";
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
			Member member = SignUpMemberRequestDto.toEntity(signUpMemberRequestDto, passwordEncoder);
			memberService.savedMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "contents/member/login";
		}

		return "redirect:/login";
	}

	@GetMapping("/login")
	public String loginView() {
		return "contents/member/login";
	}

	@GetMapping("/map")
	public String findHospital() {
		return "contents/hospital/find-hospital";
	}

	@GetMapping("/map/search")
	public String searchHospital(String keyword) {
		log.info(keyword);
		return "contents/hospital/search-hospital";
	}

}
