package com.playdata.eungae.appointment.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentDocument;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Children;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
public class ResponseAppointmentDto {

	private Long appointmentSeq;

	private String childrenName;

	private String doctorName;

	private String hospitalName;

	private String appointmentHHMM;

	public static ResponseAppointmentDto toDto(Appointment appointment) {
		return ResponseAppointmentDto.builder()
			.appointmentSeq(appointment.getAppointmentSeq())
			.childrenName(appointment.getChildren().getName())
			.doctorName(appointment.getDoctor().getName())
			.hospitalName(appointment.getHospital().getName())
			.appointmentHHMM(appointment.getAppointmentHHMM())
			.build();
	}
}
