package com.playdata.eungae.appointment.dto;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitedChangeStatusDto {

	private Long appointmentSeq;
	private AppointmentStatus status;

	public static Appointment toEntity (VisitedChangeStatusDto visitedChangeStatusDto) {
		return Appointment.builder()
			.appointmentSeq(visitedChangeStatusDto.getAppointmentSeq())
			.status(visitedChangeStatusDto.getStatus())
			.build();
	}

	public static VisitedChangeStatusDto toDto (Appointment appointment) {
		return VisitedChangeStatusDto.builder()
				.appointmentSeq(appointment.getAppointmentSeq())
				.status(appointment.getStatus())
				.build();
	}

}
