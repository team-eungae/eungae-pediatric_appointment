package com.playdata.eungae.appointment.dto;

import java.time.LocalDate;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.dto.HospitalRegisterRequestDto;
import com.playdata.eungae.member.domain.Children;
import com.playdata.eungae.member.domain.Member;

import lombok.Data;

@Data
public class AppointmentRequestDto {

	private LocalDate appointmentDate;

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
			.hospital(hospital)
			.doctor(doctor)
			.children(children)
			.member(member)
			.appointmentDate(appointmentRequestDto.getAppointmentDate())
			.appointmentHHMM(appointmentRequestDto.getAppointmentHHMM())
			.note(appointmentRequestDto.getNote())
			.build();
	}

}
