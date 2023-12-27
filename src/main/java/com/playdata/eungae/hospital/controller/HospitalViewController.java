package com.playdata.eungae.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospital")
public class HospitalViewController {
	@GetMapping
	public String hospitalDetail(){
		return "contents/hospital/hospital-details";
	}



}
