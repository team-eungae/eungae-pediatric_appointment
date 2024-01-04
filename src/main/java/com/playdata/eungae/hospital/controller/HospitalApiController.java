package com.playdata.eungae.hospital.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.hospital.dto.HospitalRegisterRequestDto;
import com.playdata.eungae.hospital.service.HospitalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hospital")
public class HospitalApiController {
	private final HospitalService hospitalService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String createHospital(@RequestBody HospitalRegisterRequestDto hospitalRegisterRequestDto) {
		hospitalService.saveHospital(hospitalRegisterRequestDto);
		return "hospital created successfully";
	}

}
