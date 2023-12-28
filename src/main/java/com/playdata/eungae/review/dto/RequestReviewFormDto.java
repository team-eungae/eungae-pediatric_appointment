package com.playdata.eungae.review.dto;

import lombok.Data;

@Data
public class RequestReviewFormDto {

	private Long hospitalSeq;
	private Long memberSeq;
	// null이 발생하지 않아 원시값으로 정의
	private int starRating;
	private String content;

}
