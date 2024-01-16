package com.playdata.eungae.appointment.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.appointment.dto.AppointmentRequestDto;
import com.playdata.eungae.appointment.service.AppointmentService;

import com.playdata.eungae.doctor.dto.DoctorResponseDto;
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

	@PostMapping("/{hospitalSeq}/appointments")
	public String saveAppointment(
		AppointmentRequestDto appointmentRequestDto,
		@AuthenticationPrincipal UserDetails member
	) {
		String email = member.getUsername();
		appointmentService.saveAppointment(appointmentRequestDto, email);
		return "/index";
	}

	@GetMapping("/{hospitalSeq}/appointments")
	public String getAppointmentForm(
		@PathVariable Long hospitalSeq,
		Model model,
		@AuthenticationPrincipal UserDetails member) {

		HospitalViewResponseDto hospital = hospitalService.findHospitalById(hospitalSeq);

		String email = member.getUsername();
		List<Children> myChildren = appointmentService.getMyChildren(email);

		List<DoctorResponseDto> doctors = appointmentService.getDoctors(hospitalSeq);

		model.addAttribute("hospital", hospital);
		model.addAttribute("children", myChildren);
		model.addAttribute("doctors", doctors);

		return "contents/appointment/appointment";
	}
}
