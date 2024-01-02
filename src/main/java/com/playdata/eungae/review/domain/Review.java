package com.playdata.eungae.review.domain;

import com.playdata.eungae.appointment.domain.Appointment;
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
import jakarta.persistence.OneToOne;
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

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "review")
	private Appointment appointment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private Member member;

	@Column(nullable = false)
	private int starRating;

	@Column(nullable = false)
	private String content;

	// @Setter setter를 엔티티에 사용하지 않고 원하는 컬럼만 변경할 수 있는 방법 생각해보기
	// 리뷰 삭제 유무를 표시하는 컬럼

}
