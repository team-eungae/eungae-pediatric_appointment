package com.playdata.eungae.hospital.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.playdata.eungae.file.FileStore;
import com.playdata.eungae.file.ResultFileStore;
import com.playdata.eungae.hospital.dto.HospitalRegisterRequestDto;
import com.playdata.eungae.hospital.dto.HospitalScheduleRequestDto;
import com.playdata.eungae.hospital.dto.KeywordSearchRequestDto;
import com.playdata.eungae.hospital.service.HospitalImageService;
import com.playdata.eungae.hospital.dto.HospitalSearchResponseDto;
import com.playdata.eungae.hospital.service.HospitalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hospital")
public class HospitalApiController {
	private final HospitalService hospitalService;
	private final HospitalImageService hospitalImageService;
	private final FileStore fileStore;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String createHospital(HospitalScheduleRequestDto hospitalScheduleRequestDto,
		HospitalRegisterRequestDto hospitalRegisterRequestDto) {
		hospitalRegisterRequestDto.setScheduleRequestDto(hospitalScheduleRequestDto);

		hospitalService.saveHospital(hospitalRegisterRequestDto);
		return "hospital created successfully";
	}

	@PostMapping("/image")
	@ResponseStatus(HttpStatus.CREATED)
	public String createHospitalImages(MultipartFile file, Long hospitalSeq) {
		ResultFileStore resultFileStore;
		resultFileStore = fileStore.storeFile(file);

		hospitalImageService.saveHospitalImage(resultFileStore, hospitalSeq);

		return "hospital Image created successfully";
	}

	@GetMapping("/around")
	@ResponseStatus(HttpStatus.OK)
	public List<HospitalSearchResponseDto> getHospitalsNearby(double longitude, double latitude) {
		return hospitalService.getHospitalsNearby(longitude, latitude);
	}

	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	public List<HospitalSearchResponseDto> getHospitalsByKeyword(
		@ModelAttribute @Valid KeywordSearchRequestDto keywordDto) {
		return hospitalService.getHospitalsBy(keywordDto);
	}
}
