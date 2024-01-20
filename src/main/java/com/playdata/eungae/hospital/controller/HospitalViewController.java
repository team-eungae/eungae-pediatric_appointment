package com.playdata.eungae.hospital.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.doctor.dto.DoctorResponseDto;
import com.playdata.eungae.doctor.service.DoctorService;
import com.playdata.eungae.hospital.dto.HospitalImageResponseDto;
import com.playdata.eungae.hospital.dto.HospitalSearchResponseDto;
import com.playdata.eungae.hospital.dto.HospitalViewResponseDto;
import com.playdata.eungae.hospital.dto.KeywordSearchRequestDto;
import com.playdata.eungae.hospital.service.HospitalImageService;
import com.playdata.eungae.hospital.service.HospitalService;
import com.playdata.eungae.review.dto.ResponseReviewDto;
import com.playdata.eungae.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/hospital")
@RequiredArgsConstructor
@Slf4j
public class HospitalViewController {
  
	private final HospitalService hospitalService;
	private final HospitalImageService hospitalImageService;
	private final DoctorService doctorService;
	private final ReviewService reviewService;

	@GetMapping("/{hospitalSeq}")
	public String getHospitalDetail(@PathVariable Long hospitalSeq, Model model) {
		HospitalViewResponseDto hospitalById = hospitalService.findHospitalById(hospitalSeq);
		List<DoctorResponseDto> doctorList = doctorService.findDoctorsByHospitalSeq(hospitalSeq);
		List<ResponseReviewDto> reviews = reviewService.findReviewsByHospitalSeq(hospitalSeq);
		List<HospitalImageResponseDto> hospitalImages = hospitalImageService.findHospitalImages(hospitalSeq);

		model.addAttribute("hospital", hospitalById);
		model.addAttribute("doctorList", doctorList);
		model.addAttribute("reviewList", reviews);
		model.addAttribute("hospitalImages", hospitalImages);
		return "contents/hospital/hospital-details";
	}

	@GetMapping("/map/search")
	public String searchHospital(KeywordSearchRequestDto keyword, Model model) {
		List<HospitalSearchResponseDto> hospitalList = hospitalService.getHospitalsBy(keyword);
		model.addAttribute("hospitalList", hospitalList);
		return "contents/hospital/search-hospital";
	}
}
