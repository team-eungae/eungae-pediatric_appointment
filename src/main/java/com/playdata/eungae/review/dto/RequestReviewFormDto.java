package com.playdata.eungae.review.dto;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.review.domain.Review;

import lombok.Data;

@Data
public class RequestReviewFormDto {

	private Long hospitalSeq;
	private Long memberSeq;
	// null이 발생하지 않아 원시값으로 정의
	private int starRating;
	private String content;

	public static Review toEntity(RequestReviewFormDto dto, Member member, Appointment appointment) {
		return Review.builder()
			.member(appointment.getMember())
			.hospital(appointment.getHospital())
			.starRating(dto.getStarRating())
			.content(dto.getContent())
			.build();
	}

}
