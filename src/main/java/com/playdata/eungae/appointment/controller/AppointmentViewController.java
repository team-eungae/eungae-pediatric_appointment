package com.playdata.eungae.appointment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.doctor.service.DoctorService;
import com.playdata.eungae.hospital.dto.HospitalViewResponseDto;
import com.playdata.eungae.hospital.service.HospitalService;
import com.playdata.eungae.member.service.ChildrenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/hospital")
@Controller
public class AppointmentViewController {

	private final HospitalService hospitalService;

	@GetMapping("/{hospitalSeq}/appointments")
	public String appointment(
		@PathVariable Long hospitalSeq,
		Model model) {
		HospitalViewResponseDto hospital = hospitalService.findHospitalById(hospitalSeq);

		model.addAttribute("hospital", hospital);

		log.warn("******** hospital: {}", hospital.getHospitalSchedule());
		return "contents/appointment/appointment";
	}
}
