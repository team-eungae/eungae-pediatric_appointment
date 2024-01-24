package com.playdata.eungae.appointment.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.playdata.eungae.appointment.service.AppointmentService;
import com.playdata.eungae.doctor.dto.DoctorResponseDto;
import com.playdata.eungae.hospital.dto.HospitalViewResponseDto;
import com.playdata.eungae.hospital.service.HospitalService;
import com.playdata.eungae.member.dto.ChildrenDto;

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
		@AuthenticationPrincipal UserDetails member
	) {
		HospitalViewResponseDto hospital = hospitalService.findHospitalById(hospitalSeq);

		String email = member.getUsername();
		List<ChildrenDto> myChildren = appointmentService.getMyChildren(email);

		List<DoctorResponseDto> doctors = appointmentService.getDoctors(hospitalSeq);

		model.addAttribute("hospital", hospital);
		model.addAttribute("children", myChildren);
		model.addAttribute("doctors", doctors);

		return "contents/appointment/appointment";
	}

	@GetMapping("/appointment/test/{appointmentSeq}/{hospitalSeq}")
	public String paymentTest(
		@PathVariable Long appointmentSeq,
		@PathVariable Long hospitalSeq,
		@RequestParam String imp_uid,
		@RequestParam(required = false) Boolean imp_success,
		@RequestParam(required = false) Boolean error_code,
		@RequestParam(required = false) String error_msg
	) {
		if (imp_success) {
			return "redirect:/hospital/" + hospitalSeq;
		}
		appointmentService.deleteAppointment(appointmentSeq);
		return "redirect:/hospital/" + hospitalSeq;
	}
}
