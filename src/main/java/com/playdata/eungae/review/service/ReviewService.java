package com.playdata.eungae.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.review.domain.Review;
import com.playdata.eungae.review.dto.RequestReviewFormDto;
import com.playdata.eungae.review.dto.ResponseReviewDto;
import com.playdata.eungae.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private int PAGE_SIZE = 20;

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
			.orElseThrow(() -> new IllegalStateException("Item not found"));
		// 리뷰의 논리적 삭제 컬럼을 Y로 바꿔주는 로직을 짜야한다
		review.remove();
	}

	@Transactional(readOnly = true)
	public Page<ResponseReviewDto> findReviews(int page, Long hospitalSeq) {
		Pageable pageConfig = PageRequest.of(
			/*정렬 기준을 정할 수 있도록 리펙토링 필요함*/
			page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt")
		);
		return reviewRepository.findAllWithMember(pageConfig, hospitalSeq)
			.map(ResponseReviewDto::toDto);
	}
}
