package com.playdata.eungae.review.dto;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.review.domain.Review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestReviewFormDto {

	// 예약 조회용
	private Long appointmentSeq;

	@NotNull(message = "별점 입력은 필수입니다.")
	private int starRating;

	@NotBlank(message = "제목 입력은 필수입니다.")
	private String title;

	@NotBlank(message = "내용 입력은 필수입니다.")
	private String content;

	public static Review toEntity(RequestReviewFormDto dto, Appointment appointment) {
		return Review.builder()
			.member(appointment.getMember())
			.title(dto.getTitle())
			.appointment(appointment)
			.hospital(appointment.getHospital())
			.starRating(dto.getStarRating())
			.content(dto.getContent())
			.build();
	}

}
