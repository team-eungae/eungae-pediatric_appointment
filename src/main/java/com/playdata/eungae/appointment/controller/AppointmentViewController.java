package com.playdata.eungae.appointment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospital")
public class AppointmentViewController {

	@GetMapping("/appointments")
	public String appointment() {
		return "contents/appointment/appointment";
	}

}
