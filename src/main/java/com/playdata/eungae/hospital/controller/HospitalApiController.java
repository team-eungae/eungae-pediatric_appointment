package com.playdata.eungae.hospital.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.hospital.dto.HospitalSearchResponseDto;
import com.playdata.eungae.hospital.dto.KeywordSearchRequestDto;
import com.playdata.eungae.hospital.service.HospitalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hospital")
public class HospitalApiController {
	private final HospitalService hospitalService;

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
