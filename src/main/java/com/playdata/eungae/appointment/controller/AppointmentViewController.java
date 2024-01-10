package com.playdata.eungae.appointment.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.appointment.service.AppointmentService;
import com.playdata.eungae.hospital.dto.HospitalViewResponseDto;
import com.playdata.eungae.hospital.service.HospitalService;
import com.playdata.eungae.member.domain.Children;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/hospital")
@Controller
public class AppointmentViewController {

	private final AppointmentService appointmentService;
	private final HospitalService hospitalService;


	@GetMapping("/{hospitalSeq}/appointments")
	public String getAppointmentForm(
		@PathVariable Long hospitalSeq,
		Model model,
		@AuthenticationPrincipal UserDetails member) {

		HospitalViewResponseDto hospital = hospitalService.findHospitalById(hospitalSeq);

		String email = member.getUsername();
		List<Children> myChildren = appointmentService.getMyChildren(email).get();

		model.addAttribute("hospital", hospital);
		model.addAttribute("children", myChildren);

		return "contents/appointment/appointment";
	}
}
