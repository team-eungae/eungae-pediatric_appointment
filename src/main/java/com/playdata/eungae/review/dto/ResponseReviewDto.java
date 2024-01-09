package com.playdata.eungae.review.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.review.domain.Review;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseReviewDto {

	private String memberName;

	private int starRating;

	private String content;

	private String writeDate;

	public static ResponseReviewDto toDto(Review review) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일");
		return ResponseReviewDto.builder()
			.memberName(review.getMember().getName())
			.starRating(review.getStarRating())
			.content(review.getContent())
			.writeDate(dateTimeFormatter.format(review.getCreatedAt()))
			.build();
	}
}
