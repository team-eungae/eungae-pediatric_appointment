package com.playdata.eungae.review.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.playdata.eungae.review.domain.Review;
import com.playdata.eungae.review.dto.RequestReviewFormDto;
import com.playdata.eungae.review.dto.ResponseReviewDto;
import com.playdata.eungae.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewApiController {

	private final ReviewService reviewService;

	@PostMapping("/my/records/{appointment_seq}/reviews")
	public ResponseEntity createReview(
		@Valid @PathVariable("appointment_seq") long appointmentSeq,
		@Valid @RequestBody RequestReviewFormDto requestReviewFormDto)
	{
		reviewService.createReview(appointmentSeq, requestReviewFormDto);
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@PatchMapping("/my/records/reviews/{review_seq}")
	public ResponseEntity removeReview(@PathVariable("review_seq") long reviewSeq) {
		reviewService.removeReview(reviewSeq);
		return ResponseEntity.ok(HttpStatus.ACCEPTED);
	}

	@GetMapping("/reviews")
	public ResponseEntity findReviews(
		@RequestParam int reviewPage,
		@RequestParam Long hospitalSeq) {
		Page<ResponseReviewDto> reviews = reviewService.findReviews(reviewPage, hospitalSeq);
		return ResponseEntity.ok().body(reviews);
	}

}
