package com.playdata.eungae.member.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/my")
public class MemberViewController {

	@GetMapping("/records")
	public String medicalRecordList() {
		return "contents/member/medical-records";
	}


	@GetMapping("/records/1")
	public String medicalRecordsDetails(){
		return "contents/member/medical-records-details";
	}

	@GetMapping("/profile")
	public String myPage() {
		return "contents/member/my-page";
	}

	@GetMapping("/appointments")
	public String myReservationList() {
		return "contents/member/my-reservations";
	}

	@GetMapping("/reviews")
	public String myReviews() {
		return "contents/member/my-review";
	}

	@GetMapping("/hospitals")
	public String regularHospitals() {
		return "contents/member/regular-hospital";
	}

	@GetMapping("/children")
	public String myChildren() {
		return "contents/member/my-children";
	}

	@GetMapping("/children/form")
	public String addMyChildren() {
		return "contents/member/my-children-add";
	}
}
