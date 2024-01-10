package com.playdata.eungae.appointment.dto;

import java.time.LocalDate;

import com.playdata.eungae.appointment.domain.Appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDetailMedicalHistoryDto {

	private Long appointmentSeq;

	private String childrenName;

	private String doctorName;

	private String hospitalName;

	private boolean writeReview;

	private LocalDate appointmentDate;

	private String appointmentHHMM;

	public static ResponseDetailMedicalHistoryDto toDto(Appointment appointment) {
		return ResponseDetailMedicalHistoryDto.builder()
			.appointmentSeq(appointment.getAppointmentSeq())
			.childrenName(appointment.getChildren().getName())
			.doctorName(appointment.getDoctor().getName())
			.hospitalName(appointment.getHospital().getName())
			.appointmentDate(appointment.getAppointmentDate())
			.appointmentHHMM(appointment.getAppointmentHHMM())
			.writeReview(isWriteReview(appointment.getReviewSeq()))
			.build();
	}

	private static boolean isWriteReview(Long reviewSeq) {
		if (reviewSeq == null) {
			return false;
		}
		return true;
	}
}
