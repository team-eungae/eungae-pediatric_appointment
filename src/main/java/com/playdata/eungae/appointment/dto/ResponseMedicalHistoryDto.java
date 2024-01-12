package com.playdata.eungae.appointment.dto;

import java.time.LocalDate;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMedicalHistoryDto {

	private Long appointmentSeq;
	private String childrenName;
	private String doctorName;
	private String hospitalName;
	private LocalDate appointmentDate;
	private String appointmentHHMM;
	private String note;
	private Long reviewSeq;

	public static ResponseMedicalHistoryDto toDto(Appointment appointment){
		return ResponseMedicalHistoryDto.builder()
			.reviewSeq(appointment.getReviewSeq())
			.appointmentSeq(appointment.getAppointmentSeq())
			.childrenName(appointment.getChildren().getName())
			.doctorName(appointment.getDoctor().getName())
			.hospitalName(appointment.getHospital().getName())
			.appointmentDate(appointment.getAppointmentDate())
			.appointmentHHMM(appointment.getAppointmentHHMM())
			.note(appointment.getNote())
			.build();
	}
}
