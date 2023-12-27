package com.playdata.eungae.hospital.domain;

import java.util.Date;

import com.playdata.eungae.base.BaseEntity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "hospital_schedule")
@Entity
public class HospitalSchedule extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long hospitalScheduleSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_seq")
	private Hospital hospital;

	@Column(nullable = false)
	private Date days;  // 0: 월요일 ~ 6: 일요일

	@Column(nullable = false)
	private String openingTime;

	@Column(nullable = false)
	private String closingTime;
}
