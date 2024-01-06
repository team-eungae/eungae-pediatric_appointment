package com.playdata.eungae.appointment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospital")
public class AppointmentViewController {

	@GetMapping("/{hospitalSeq}/appointments")
	public String appointment(@PathVariable Long hospitalSeq) {
		return "contents/appointment/appointment";
	}

}
