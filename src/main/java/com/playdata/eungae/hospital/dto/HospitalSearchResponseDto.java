package com.playdata.eungae.hospital.dto;

import java.util.List;

import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.review.domain.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalSearchResponseDto {
	private Long hospitalSeq;
	private String name;
	private String hospitalId;
	private String notice;
	private int deposit; //예약금
	private String contact; //연락처
	private String address;
	private double longitude;
	private double latitude;
	private double averageRating;
	private int totalReview;
	private int starCount;
	private String hospitalThumbnail;
	private int currentWaitingCount;

	public static HospitalSearchResponseDto toDto(Hospital entity) {
		List<Review> reviews = entity.getReviews();

		double averageRating = reviews.stream()
			.mapToDouble(Review::getStarRating)
			.average()
			.orElse(0.0);

		return HospitalSearchResponseDto.builder()
			.hospitalSeq(entity.getHospitalSeq())
			.name(entity.getName())
			.hospitalId(entity.getHospitalId())
			.notice(entity.getNotice())
			.deposit(entity.getDeposit())
			.contact(entity.getContact())
			.address(entity.getAddress())
			.longitude(entity.getXCoordinate())
			.latitude(entity.getYCoordinate())
			.averageRating(Math.round(averageRating))
			.totalReview(reviews.size())
			.starCount((int)averageRating)
			.hospitalThumbnail(entity.getHospitalImageList().isEmpty() ?
				null : entity.getHospitalImageList().get(0).getStoreFileName())
			.currentWaitingCount(0)
			.build();
	}
}