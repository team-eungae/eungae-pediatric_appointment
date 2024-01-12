package com.playdata.eungae.member.dto;

import java.util.List;

import com.playdata.eungae.member.domain.FavoritesHospital;
import com.playdata.eungae.review.domain.Review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseFavoritesHospitalDto {

	// 병원 대표사진 가져와야함
	private Long hospitalSeq;
	private String hospitalName;
	private String hospitalAddress;
	private String averageRating;
	private Long totalReview;

	public static ResponseFavoritesHospitalDto toDto(FavoritesHospital favoritesHospital) {

		List<Review> reviews = favoritesHospital.getHospital().getReviews();

		double averageRating = reviews.stream()
			.mapToDouble(Review::getStarRating)
			.average()
			.orElse(0.0);

		String formattedAverageRating = String.format("%.1f", averageRating);

		return ResponseFavoritesHospitalDto.builder()
			.hospitalSeq(favoritesHospital.getHospital().getHospitalSeq())
			.hospitalName(favoritesHospital.getHospital().getName())
			.hospitalAddress(favoritesHospital.getHospital().getAddress())
			.averageRating(formattedAverageRating)
			.totalReview((long)reviews.size())
			.build();
	}

}
