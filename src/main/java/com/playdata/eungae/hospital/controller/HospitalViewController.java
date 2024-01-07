package com.playdata.eungae.hospital.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.doctor.dto.DoctorViewResponseDto;
import com.playdata.eungae.doctor.service.DoctorService;
import com.playdata.eungae.hospital.dto.HospitalViewResponseDto;
import com.playdata.eungae.hospital.service.HospitalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/hospital")
@RequiredArgsConstructor
@Slf4j
public class HospitalViewController {
	private final HospitalService hospitalService;
	private final DoctorService doctorService;

	@GetMapping("/{hospitalSeq}")
	public String hospitalDetail(@PathVariable Long hospitalSeq, Model model){
		HospitalViewResponseDto hospitalById = hospitalService.findHospitalById(hospitalSeq);
		List<DoctorViewResponseDto> doctorList = doctorService.findDoctorsByHospitalId(hospitalSeq);
		model.addAttribute("hospital",hospitalById);
		model.addAttribute("doctorList",doctorList);
		return "contents/hospital/hospital-details";
	}
}
