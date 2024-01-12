package com.playdata.eungae.appointment.dto;

import java.time.LocalDate;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseAppointmentDto {

	private Long appointmentSeq;

	private Long hospitalSeq;

	private String childrenName;

	private String doctorName;

	private String hospitalName;

	private LocalDate appointmentDate;

	private String appointmentHHMM;

	private AppointmentStatus status;

	public static ResponseAppointmentDto toDto(Appointment appointment) {
		return ResponseAppointmentDto.builder()
			.status(appointment.getStatus())
			.hospitalSeq(appointment.getHospital().getHospitalSeq())
			.appointmentSeq(appointment.getAppointmentSeq())
			.childrenName(appointment.getChildren().getName())
			.doctorName(appointment.getDoctor().getName())
			.hospitalName(appointment.getHospital().getName())
			.appointmentDate(appointment.getAppointmentDate())
			.appointmentHHMM(appointment.getAppointmentHHMM())
			.build();
	}
}
