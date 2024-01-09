package com.playdata.eungae.appointment.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.playdata.eungae.appointment.domain.Appointment;

import lombok.Data;

@Data
public class AppointmentRequestDto {

	private LocalDate appointmentDate;

	private String appointmentHHMM;

	private long children;

	private long doctor;

	private String note;

	public static Appointment toEntity(
		AppointmentRequestDto appointmentRequestDto) {
		return Appointment.builder()
			.appointmentDate(appointmentRequestDto.getAppointmentDate())
			.appointmentHHMM(appointmentRequestDto.getAppointmentHHMM())
			.build();
	}
}
