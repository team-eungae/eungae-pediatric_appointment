package com.playdata.eungae.member.domain;

import java.util.ArrayList;
import java.util.List;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "children")
public class Children extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long childrenSeq;

	@JoinColumn(name = "member_seq")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Builder.Default
	@OneToMany(mappedBy = "children")
	private List<Appointment> appointmentList = new ArrayList<>();

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String birthDate;

	@Column(nullable = false)
	private String gender;

	@Column
	private String etcInfo;

	@Column
	private String profileImage;

	// 연관관계 편의 메서드
	public void setMember(Member member) {
		this.member = member;
		member.addChildren(this);
	}

}