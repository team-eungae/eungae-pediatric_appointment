package com.playdata.eungae.appointment.dto;

import java.time.LocalDate;

import com.playdata.eungae.appointment.domain.Appointment;

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

	public static ResponseMedicalHistoryDto toDto(Appointment entity){
		return ResponseMedicalHistoryDto.builder()
			.appointmentSeq(entity.getAppointmentSeq())
			.childrenName(entity.getChildren().getName())
			.doctorName(entity.getDoctor().getName())
			.hospitalName(entity.getHospital().getName())
			.appointmentDate(entity.getAppointmentDate())
			.appointmentHHMM(entity.getAppointmentHHMM())
			.note(entity.getNote())
			.build();
	}
}
