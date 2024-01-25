package com.playdata.eungae.review.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentStatus;
import com.playdata.eungae.appointment.repository.AppointmentRepository;
import com.playdata.eungae.review.domain.Review;
import com.playdata.eungae.review.dto.RequestReviewFormDto;
import com.playdata.eungae.review.dto.ResponseReviewDto;
import com.playdata.eungae.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	private static final int PAGE_SIZE = 20;
	private final ReviewRepository reviewRepository;
	private final AppointmentRepository appointmentRepository;

	@Transactional
	public void createReview(RequestReviewFormDto requestReviewFormDto) {

		Appointment appointment = appointmentRepository.findByIdWhitHospital(requestReviewFormDto.getAppointmentSeq())
			.orElseThrow(() -> new IllegalStateException("Can not found Appointment, appointmentSeq = {%d}"
				.formatted(requestReviewFormDto.getAppointmentSeq())));

		// 진단 완료가 된 상태가 아니면 exception 발생
		if (appointment.getStatus() != AppointmentStatus.DIAGNOSIS) {
			throw new IllegalStateException("appointment is not diagnosed");
		}

		Review review = RequestReviewFormDto.toEntity(requestReviewFormDto, appointment);

		Review reviewEntity = reviewRepository.save(review);

		appointment.setReviewSeq(reviewEntity.getReviewSeq());
	}

	@Transactional
	public void removeReview(long reviewSeq) {
		Review review = reviewRepository.findById(reviewSeq)
			.orElseThrow(() -> new IllegalStateException("Can not found Review, reviewSeq = {%d}".formatted(reviewSeq)));
		review.deleted();
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

	@Transactional(readOnly = true)
	public List<ResponseReviewDto> findReviewsByHospitalSeq(Long hospitalSeq) {
		List<Review> reviews = reviewRepository.findAllByHospitalHospitalSeq(hospitalSeq);

		return reviews.stream()
			.map(ResponseReviewDto::toDto)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ResponseReviewDto> findReviewsByMemberEmail(String memberEmail) {

		List<Review> reviews = reviewRepository.findReviewsByMemberEmail(memberEmail);

		return reviews.stream()
			.map(ResponseReviewDto::toDto)
			.collect(Collectors.toList());
	}
}
