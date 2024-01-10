package com.playdata.eungae.hospital.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		try {
			resultFileStore = fileStore.storeFile(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
	public List<HospitalSearchResponseDto> getAllByKeyword(String keyword){
		return hospitalService.findAllByKeyword(keyword);
	}
}
