package com.playdata.eungae.review.dto;

import java.time.format.DateTimeFormatter;

import com.playdata.eungae.review.domain.Review;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseReviewDto {

	private Long reviewSeq;

	private Long hospitalSeq;

	private String hospitalName;

	private String memberName;

	private int starRating;

	private String title;

	private String content;

	private String writeDate;

	public static ResponseReviewDto toDto(Review review) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일");
		return ResponseReviewDto.builder()
			.reviewSeq(review.getReviewSeq())
			.hospitalSeq(review.getHospital().getHospitalSeq())
			.hospitalName(review.getHospital().getName())
			.title(review.getTitle())
			.memberName(review.getMember().getName())
			.starRating(review.getStarRating())
			.content(review.getContent())
			.writeDate(dateTimeFormatter.format(review.getCreatedAt()))
			.build();
	}
}
