package com.playdata.eungae.doctor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.playdata.eungae.doctor.dto.DoctorRegisterRequestDto;
import com.playdata.eungae.doctor.service.DoctorService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RequestMapping("/hospital")
@Controller
public class DoctorViewController {
	private final DoctorService doctorService;

	@GetMapping("/doctors/form")
	public String createHospitalDoctorView(){
		return "contents/hospital/create-doctor";
	}

	@PostMapping("/doctors")
	@ResponseBody
	public String createHospitalDoctor(@ModelAttribute DoctorRegisterRequestDto doctorRegisterRequestDto){
		doctorService.createDoctor(doctorRegisterRequestDto);
		return "Doctor created successfully";
	}
}
