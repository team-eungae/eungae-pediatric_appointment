package com.playdata.eungae.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.review.dto.ResponseReviewDto;
import com.playdata.eungae.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewApiController {

	private final ReviewService reviewService;

	@GetMapping("/reviews")
	@ResponseStatus(HttpStatus.OK)
	public Page<ResponseReviewDto> findReviews(
		@RequestParam int reviewPage,
		@RequestParam Long hospitalSeq) {
		return reviewService.findReviews(reviewPage, hospitalSeq);
	}

}
