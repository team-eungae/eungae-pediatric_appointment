package com.playdata.eungae.review.domain;

import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "review")
@Entity
public class Review extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long reviewSeq;

	@ManyToOne
	@JoinColumn(name = "hospital_seq")
	private Hospital hospital;

	@ManyToOne
	@JoinColumn(name = "member_seq")
	private Member member;

	@Column(nullable = false)
	private int starRating;

	@Column(nullable = false)
	private String content;
}
