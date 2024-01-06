package com.playdata.eungae.appointment.dto;

import java.time.LocalDateTime;

import com.playdata.eungae.appointment.domain.Appointment;

import lombok.Data;

@Data
public class AppointmentRequestDto {

	private LocalDateTime appointmentDate;

	private String appointmentHour;

	private String appointmentMinute;

	private long children;

	private long doctor;

	private String note;

	public static Appointment toEntity(
		AppointmentRequestDto appointmentRequestDto) {
		return Appointment.builder().build();
	}
}
