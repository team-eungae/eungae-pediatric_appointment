package com.playdata.eungae.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.admin.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

	private final AdminService adminService;

	// @PostMapping("/open-api/hospital")
	@GetMapping("/open-api/hospital")
	public void savePublicHospitalData() {
		adminService.savePublicHospitalData();
	}

	@GetMapping("/redis-insert/hospital")
	public void saveHospitalsToRedis() {
		adminService.saveAllHospitalsToRedis();
	}

}
