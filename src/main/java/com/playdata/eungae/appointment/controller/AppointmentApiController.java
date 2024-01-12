package com.playdata.eungae.appointment.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/appointment/time")
	@ResponseStatus(HttpStatus.OK)
	public List<LocalTime> findTime(
		@RequestParam String appointmentDate,
		@RequestParam Integer appointmentDayOfWeek,
		@RequestParam Long doctorSeq,
		@RequestParam Long hospitalSeq) {

		return appointmentService.createAppointmentPossibleTime(appointmentDate, appointmentDayOfWeek, doctorSeq, hospitalSeq);
	}

	@PatchMapping("/my/appointments")
	@ResponseStatus(HttpStatus.OK)
	public ResponseAppointmentDto deleteAppointment(@RequestBody RequestAppointmentDeleteDto requestAppointmentDeleteDto) {
		return appointmentService.deleteAppointment(requestAppointmentDeleteDto.getAppointmentSeq());
	}
}
