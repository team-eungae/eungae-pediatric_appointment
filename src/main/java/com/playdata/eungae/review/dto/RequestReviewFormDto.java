package com.playdata.eungae.review.dto;

import lombok.Data;

@Data
public class RequestReviewFormDto {

	private Long hospital_seq;
	private Long member_seq;
	private int starRating;
	private String content;

}
