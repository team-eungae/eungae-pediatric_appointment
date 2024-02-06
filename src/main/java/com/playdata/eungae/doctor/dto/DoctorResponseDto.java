package com.playdata.eungae.doctor.dto;

import com.playdata.eungae.doctor.domain.Doctor;

import com.playdata.eungae.doctor.domain.DoctorStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorResponseDto {
	private Long doctorSeq;
	private String name;
	private DoctorStatus status;
	private int treatmentPossible;
	private String doctorProfileImage;

	public static DoctorResponseDto toDto(Doctor doctor){
		return DoctorResponseDto.builder()
			.doctorSeq(doctor.getDoctorSeq())
			.name(doctor.getName())
			.status(doctor.getStatus())
			.treatmentPossible(doctor.getTreatmentPossible())
			.doctorProfileImage(doctor.getDoctorProfileImage())
			.build();
	}
}
