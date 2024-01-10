package com.playdata.eungae.review.dto;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.review.domain.Review;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestReviewFormDto {

	// 예약 조회용
	private Long appointmentSeq;

	private int starRating;

	@NotBlank
	private String title;

	@NotBlank
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
