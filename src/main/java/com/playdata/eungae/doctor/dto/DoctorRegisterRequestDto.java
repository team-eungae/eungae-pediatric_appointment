package com.playdata.eungae.doctor.dto;



import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.file.FileStore;
import com.playdata.eungae.file.ResultFileStore;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
public class DoctorRegisterRequestDto {

	private String name;
	private int treatmentPossible;
	private MultipartFile profileImage;

	public Doctor toEntity(DoctorRegisterRequestDto doctorRegisterRequestDto) throws IOException {
		FileStore fileStore = new FileStore();
		ResultFileStore resultFileStore = fileStore.storeProfileFile(doctorRegisterRequestDto.getProfileImage());
		return Doctor.builder()
			.name(doctorRegisterRequestDto.getName())
			.treatmentPossible(doctorRegisterRequestDto.getTreatmentPossible())
			.profileImage(resultFileStore.getStoreFileName())
			.build();
	}
}
