package com.playdata.eungae.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.review.domain.Review;
import com.playdata.eungae.review.dto.RequestReviewFormDto;
import com.playdata.eungae.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;

	@Transactional
	public void createReview(long appointmentSeq, RequestReviewFormDto requestReviewFormDto) {
		// appointmentSeq로 appointment Entity 조회
		// 최적화를 위해 appointment Entity는 Hospital Entity도 Fetch join으로 같이 가져와야 한다.

		// 조회된 appointmentSeq 엔티티를 RequestReviewFormDto.toEntity에 같이 넘겨 Review Entity 생성

		// reviewRepository.save(ReviewEntity); 로 DB에 저장
	}

	@Transactional
	public void removeReview(long reviewSeq) {
		Review review = reviewRepository.findById(reviewSeq)
			.orElseThrow(() -> new NullPointerException("리뷰를 찾을 수 없습니다. Exception을 만들어서 처리해보자"));
		// 리뷰의 논리적 삭제 컬럼을 Y로 바꿔주는 로직을 짜야한다
		review.remove();
	}
}
