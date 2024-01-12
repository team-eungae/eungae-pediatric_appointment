package com.playdata.eungae.doctor.dto;




import org.springframework.web.multipart.MultipartFile;

import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.file.FileStore;
import com.playdata.eungae.file.ResultFileStore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorRegisterRequestDto {

	private String name;
	private int treatmentPossible;
	private MultipartFile DoctorProfileImage;

	public Doctor toEntity(DoctorRegisterRequestDto doctorRegisterRequestDto){
		FileStore fileStore = new FileStore();
		ResultFileStore resultFileStore = fileStore.storeProfileFile(doctorRegisterRequestDto.getDoctorProfileImage());
		return Doctor.builder()
			.name(doctorRegisterRequestDto.getName())
			.treatmentPossible(doctorRegisterRequestDto.getTreatmentPossible())
			.DoctorProfileImage(resultFileStore.getStoreFileName())
			.build();
	}
}
