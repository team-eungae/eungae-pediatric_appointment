package com.playdata.eungae.review.domain;

import javax.security.auth.login.LoginException;

import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.review.dto.RequestReviewFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "review")
@Entity
@Builder
public class Review extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long reviewSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_seq")
	private Hospital hospital;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private Member member;

	@Column(nullable = false)
	private int starRating;

	@Column(nullable = false)
	private String content;

	public static Review from (RequestReviewFormDto dto) {
		if (dto.getMember_seq() == null) {
			// throw new LoginException("로그인을 해야 리뷰를 작성할 수 있습니다");
		}
		return Review.builder()
			// .hospital(/* dto에 hospital_seq로 병원 조회해서 엔티티 집어넣기 */ dto.getHospital_seq())
			// .member(dto.getMember_seq())
			.starRating(dto.getStarRating())
			.content(dto.getContent())
			.build();
	}
}
