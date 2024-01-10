package com.playdata.eungae.appointment.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.appointment.dto.ResponseAppointmentDto;
import com.playdata.eungae.appointment.dto.ResponseDetailMedicalHistoryDto;
import com.playdata.eungae.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my")
public class AppointmentApiController {

	private final AppointmentService appointmentService;

	@GetMapping("/appointments")
	@ResponseStatus(HttpStatus.OK)
	public Page<ResponseAppointmentDto> findAppointment(
		@RequestParam int pageNumber,
		@RequestParam Long memberSeq
	) {
		return appointmentService.findAppointment(pageNumber, memberSeq);
	}

}
