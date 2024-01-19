package com.playdata.eungae.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.playdata.eungae.appointment.service.AppointmentService;
import com.playdata.eungae.review.dto.RequestReviewFormDto;
import com.playdata.eungae.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewViewController {

	private final ReviewService reviewService;
	private final AppointmentService appointmentService;

	@GetMapping("/post")
	public String writeReview(
		Model model,
		@RequestParam Long appointmentSeq
	) {
		appointmentService.checkAppointmentStatus(appointmentSeq);
		model.addAttribute("requestAppointmentSeq", appointmentSeq);
		model.addAttribute("requestReviewFormDto", new RequestReviewFormDto());
		return "contents/member/review-write";
	}

	@PostMapping("/post")
	public String createReview(
		Model model,
		@Valid RequestReviewFormDto requestReviewFormDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("requestAppointmentSeq", requestReviewFormDto.getAppointmentSeq());
			return "contents/member/review-write";
		}
		reviewService.createReview(requestReviewFormDto);
		return "redirect:/my/reviews";
	}

}
