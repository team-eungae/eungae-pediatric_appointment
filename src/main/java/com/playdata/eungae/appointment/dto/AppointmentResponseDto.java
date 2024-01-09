package com.playdata.eungae.appointment.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentResponseDto {

	private Long appointmentSeq;
	// private Children children;
	private String childrenName;
	// private Doctor doctor;
	private String doctorName;
	// private Hospital hospital;
	private String hospitalName;
	// private Review review;

	private LocalDate appointmentDate;
	private String appointmentHHMM;
	private String note;

	/*
	현재 예약 등록기능이 구현되지 않아 예약등록이 구현된 후에 수정 예정입니다.
	public static AppointmentResponseDto toDto(Appointment entity){
		return Appointment.builder()
			.appointmentSeq(entity.getAppointmentSeq())
			.children(entity.getChildren().toResponseDto())
			.doctor(entity.getDoctor().toResponseDto())
			.hospital(entity.getHospital().toResponseDto())
			.review(entity.getReview().toResponseDto())
			.appointmentDate(entity.getAppointmentDate())
			.appointmentHour(entity.getAppointmentHour())
			.appointmentMinute(entity.getAppointmentMinute())
			.note(entity.getNote())
			.build();
	}
	*/
}
