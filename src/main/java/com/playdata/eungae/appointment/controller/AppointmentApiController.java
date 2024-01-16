package com.playdata.eungae.appointment.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.appointment.dto.AppointmentRequestDto;
import com.playdata.eungae.appointment.dto.RequestAppointmentDeleteDto;
import com.playdata.eungae.appointment.dto.ResponseAppointmentDto;
import com.playdata.eungae.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AppointmentApiController {

	private final AppointmentService appointmentService;

	@PostMapping("/{hospitalSeq}/appointments")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveAppointment(
		@RequestBody AppointmentRequestDto appointmentRequestDto,
		@AuthenticationPrincipal UserDetails member
	) {
		String email = member.getUsername();
		appointmentService.saveAppointment(appointmentRequestDto, email);
		return "success";
	}

	@GetMapping("/appointment/time")
	@ResponseStatus(HttpStatus.OK)
	public List<Object> findTime(
		@RequestParam String appointmentDate,
		@RequestParam Integer appointmentDayOfWeek,
		@RequestParam Long doctorSeq,
		@RequestParam Long hospitalSeq,
		@RequestParam Long childrenSeq
	) {
		List<Object> result = new ArrayList<>();
		List<LocalTime> appointmentPossibleTime = appointmentService.createAppointmentPossibleTime(appointmentDate,
			appointmentDayOfWeek, doctorSeq, hospitalSeq, childrenSeq);
		boolean childrenAppointmentStatus = appointmentService.getChildrenAppointmentTime(childrenSeq,
			appointmentDate);
		result.add(appointmentPossibleTime);
		result.add(childrenAppointmentStatus);
		return result;

	}

	@PatchMapping("/my/appointments")
	@ResponseStatus(HttpStatus.OK)
	public ResponseAppointmentDto deleteAppointment(@RequestBody RequestAppointmentDeleteDto requestAppointmentDeleteDto) {
		return appointmentService.deleteAppointment(requestAppointmentDeleteDto.getAppointmentSeq());
	}
}
