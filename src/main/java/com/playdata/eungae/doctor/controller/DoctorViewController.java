package com.playdata.eungae.doctor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.doctor.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/hospital")
@Controller
public class DoctorViewController {
	private final DoctorService doctorService;

	@GetMapping("/doctors/{doctorSeq}")
	public String deleteHospitalDoctor(@PathVariable Long doctorSeq) {
		doctorService.deleteDoctor(doctorSeq);

		return "redirect:/hospital/doctors/form";
	}
}
