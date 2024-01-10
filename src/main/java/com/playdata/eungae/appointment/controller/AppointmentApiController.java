package com.playdata.eungae.appointment.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/api")
public class AppointmentApiController {

	private final AppointmentService appointmentService;

	@GetMapping("/my/medical_history")
	@ResponseStatus(HttpStatus.OK)
	public ResponseDetailMedicalHistoryDto findMedicalHistory(@RequestParam Long appointmentSeq) {
		// 예약 내역 조회
		// RestController 말고 Controller에서 모델 엔 뷰로 화면에 전달해야함
		 return appointmentService.findMedicalHistory(appointmentSeq);
	}

	@GetMapping("/my/appointments")
	@ResponseStatus(HttpStatus.OK)
	public Page<ResponseAppointmentDto> findAppointment(
		@RequestParam int pageNumber,
		@RequestParam Long memberSeq
	) {
		return appointmentService.findAppointment(pageNumber, memberSeq);
	}

	@GetMapping("/appointment/time")
	@ResponseStatus(HttpStatus.OK)
	public List<LocalTime> findTime(
		@RequestParam String appointmentDate,
		@RequestParam Integer appointmentDayOfWeek,
		@RequestParam Long doctorSeq,
		@RequestParam Long hospitalSeq) {

		log.info("********** {}", hospitalSeq);
		return appointmentService.createAppointmentPossibleTime(appointmentDate, appointmentDayOfWeek, doctorSeq, hospitalSeq);
	}

}
