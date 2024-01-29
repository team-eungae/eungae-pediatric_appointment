package com.playdata.eungae.review.domain;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Member;

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

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long reviewSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_seq")
	private Hospital hospital;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appiontment_seq")
	private Appointment appointment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private Member member;

	@Column(nullable = false)
	private int starRating;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

}
