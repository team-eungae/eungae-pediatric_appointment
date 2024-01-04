package com.playdata.eungae.review.dto;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.review.domain.Review;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestReviewFormDto {

	// null이 발생하지 않는 값을 원시값으로 정의하는것이 아닌 Bean Validation으로 검증할 필요가 있는지
	private int starRating;
	@NotBlank
	private String content;

	public static Review toEntity(RequestReviewFormDto dto, Appointment appointment) {
		return Review.builder()
			.member(appointment.getMember())
			.appointment(appointment)
			.hospital(appointment.getHospital())
			.starRating(dto.getStarRating())
			.content(dto.getContent())
			.build();
	}

}
