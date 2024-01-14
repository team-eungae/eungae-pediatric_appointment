
package com.playdata.eungae.doctor.dto;

import com.playdata.eungae.doctor.domain.Doctor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorResponseDto {
	private Long doctorSeq;
	private String name;
	private String status;
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
