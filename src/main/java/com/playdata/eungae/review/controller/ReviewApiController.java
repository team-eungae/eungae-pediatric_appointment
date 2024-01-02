package com.playdata.eungae.review.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.playdata.eungae.review.domain.Review;
import com.playdata.eungae.review.dto.RequestReviewFormDto;
import com.playdata.eungae.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my/records")
public class ReviewApiController {

	private final ReviewService reviewService;

	@PostMapping("/{appointment_seq}/reviews")
	public ResponseEntity<?> createReview(
		@Valid @PathVariable("appointment_seq") long appointmentSeq,
		@Valid @RequestBody RequestReviewFormDto requestReviewFormDto)
	{
		reviewService.createReview(appointmentSeq, requestReviewFormDto);
		// 리턴 타입을 어떻게 처리할지
		// body에 return 성공 message를 같이 보내자
		// Result 객체를 만들어서 처리하는 방법도 찾아보자
		return new ResponseEntity<String>("리뷰 작성 성공", HttpStatus.CREATED);
	}

	@PatchMapping("/reviews/{review_seq}")
	public ResponseEntity<?> createReview(@PathVariable("review_seq") long reviewSeq) {
		reviewService.removeReview(reviewSeq);
		return new ResponseEntity<String>("리뷰 삭제 성공", HttpStatus.OK);
	}

}
