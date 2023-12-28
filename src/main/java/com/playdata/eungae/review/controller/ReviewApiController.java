package com.playdata.eungae.review.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.playdata.eungae.review.domain.Review;
import com.playdata.eungae.review.dto.RequestReviewFormDto;
import com.playdata.eungae.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my/records")
public class ReviewApiController {

	private final ReviewService reviewService;

	@PostMapping("/{appointment_seq}/reviews")
	public ResponseEntity<?> createReview(@PathVariable("appointment_seq") long appointmentSeq, @RequestBody RequestReviewFormDto requestReviewFormDto) {
		//
		// Review reviewEntity = Review.from(requestReviewFormDto);
		// reviewService.createReview(reviewEntity);
		return ResponseEntity.ok(HttpStatus.OK);
	}


}
