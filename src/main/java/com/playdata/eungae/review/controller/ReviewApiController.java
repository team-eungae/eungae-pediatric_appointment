package com.playdata.eungae.review.controller;

import java.security.Principal;

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

@ControllerAdvice
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my/records")
public class ReviewApiController {

	private final ReviewService reviewService;

	@PostMapping("/{appointment_seq}/reviews")
	public void createReview(@PathVariable Long appointment_seq, Principal principal, @RequestBody RequestReviewFormDto dto) {
		Review reviewEntity = Review.from(dto);
		reviewService.createReview(reviewEntity);
	}


}
