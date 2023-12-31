package com.playdata.eungae.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.review.dto.RequestReviewFormDto;
import com.playdata.eungae.review.dto.ResponseReviewDto;
import com.playdata.eungae.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewApiController {

	private final ReviewService reviewService;

	@PostMapping("/my/records/{appointment_seq}/reviews")
	@ResponseStatus(HttpStatus.CREATED)
	public String createReview(
		@Valid @PathVariable("appointment_seq") long appointmentSeq,
		@Valid @RequestBody RequestReviewFormDto requestReviewFormDto) {
		reviewService.createReview(appointmentSeq, requestReviewFormDto);
		return "Review have been successfully created";
	}

	@PatchMapping("/my/records/reviews/{review_seq}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String removeReview(@PathVariable("review_seq") long reviewSeq) {
		reviewService.removeReview(reviewSeq);
		return "Review have been successfully deleted";
	}

	@GetMapping("/reviews")
	@ResponseStatus(HttpStatus.OK)
	public Page<ResponseReviewDto> findReviews(
		@RequestParam int reviewPage,
		@RequestParam Long hospitalSeq) {
		return reviewService.findReviews(reviewPage, hospitalSeq);
	}

}
