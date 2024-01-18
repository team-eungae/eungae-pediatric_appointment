package com.playdata.eungae.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospital")
public class HospitalUserController {

	@GetMapping("/login")
	public String hospitalLogin(){ return "hospital-contents/hospital-login";}

	@GetMapping("/main")
	public String hospitalMain(){
		return "hospital-contents/hospital-admin";
	}

	@GetMapping("/doctor/form")
	public String hospitalDoctorForm(){
		return "hospital-contents/hospital-admin-doctor-form";
	}

	@GetMapping("/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "contents/member/login";
	}
}
