package com.playdata.eungae.hospital.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.playdata.eungae.file.FileStore;
import com.playdata.eungae.file.ResultFileStore;
import com.playdata.eungae.hospital.dto.HospitalRegisterRequestDto;
import com.playdata.eungae.hospital.dto.HospitalScheduleRequestDto;
import com.playdata.eungae.hospital.service.HospitalImageService;
import com.playdata.eungae.hospital.dto.HospitalSearchResponseDto;
import com.playdata.eungae.hospital.service.HospitalService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hospital")
@Validated
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
	public List<HospitalSearchResponseDto> getAllNearbyHospital(double longitude, double latitude) {
		List<HospitalSearchResponseDto> nearbyHospital = hospitalService.findAllNearbyHospital(longitude, latitude);
		return nearbyHospital;
	}

	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	public List<HospitalSearchResponseDto> getAllHospitalSearchResponseDtoByKeyword(
		@RequestParam
		@NotBlank(message = "Keyword can not contain blank")
		@Size(min = 2, message = "Keyword can not be less than two letters") String keyword) {
		return hospitalService.findAllByKeyword(keyword);
	}
}
