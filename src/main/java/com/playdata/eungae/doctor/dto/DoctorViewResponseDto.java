
package com.playdata.eungae.doctor.dto;

import com.playdata.eungae.doctor.domain.Doctor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorViewResponseDto{
	private Long doctorSeq;
	private String name;
	private String status;
	private int treatmentPossible;
	private String profileImage;

	public static DoctorViewResponseDto toDto(Doctor doctor){
		return DoctorViewResponseDto.builder()
			.doctorSeq(doctor.getDoctorSeq())
			.name(doctor.getName())
			.status(doctor.getStatus())
			.treatmentPossible(doctor.getTreatmentPossible())
			.profileImage(doctor.getProfileImageStoreName())
			.build();
	}
}
