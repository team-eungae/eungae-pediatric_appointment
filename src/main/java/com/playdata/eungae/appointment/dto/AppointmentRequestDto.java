package com.playdata.eungae.appointment.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentStatus;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Children;
import com.playdata.eungae.member.domain.Member;

import lombok.Data;

@Data
public class AppointmentRequestDto {

	private String appointmentDate;

	private String appointmentHHMM;

	private long hospitalSeq;

	private long childrenSeq;

	private long doctorSeq;

	private String note;

	public static Appointment toEntity(AppointmentRequestDto appointmentRequestDto,
		Hospital hospital,
		Children children,
		Doctor doctor,
		Member member) {
		return Appointment.builder()
			// 예약 상태 추가
			.status(AppointmentStatus.APPOINTMENT)
			.hospital(hospital)
			.doctor(doctor)
			.children(children)
			.member(member)
			.appointmentDate(LocalDate.parse(appointmentRequestDto.getAppointmentDate(), DateTimeFormatter.ISO_DATE))
			.appointmentHHMM(appointmentRequestDto.getAppointmentHHMM().replace(":", ""))
			.note(appointmentRequestDto.getNote())
			.build();
	}

}
