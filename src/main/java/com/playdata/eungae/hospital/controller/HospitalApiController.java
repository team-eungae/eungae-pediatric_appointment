package com.playdata.eungae.hospital.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.hospital.dto.HospitalRegisterRequestDto;
import com.playdata.eungae.hospital.service.HospitalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hospital")
public class HospitalApiController {
	private final HospitalService hospitalService;


	@PostMapping()
	public ResponseEntity<String> createHospital(@RequestBody HospitalRegisterRequestDto dto){
		hospitalService.saveHospital(dto);
		return new ResponseEntity<String>("병원 가입 완료되었습니다.",HttpStatus.CREATED);
	}





}
