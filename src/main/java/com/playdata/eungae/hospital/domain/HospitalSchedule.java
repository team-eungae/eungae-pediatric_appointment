package com.playdata.eungae.hospital.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

import com.playdata.eungae.base.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "hospital_schedule")
@Entity
public class HospitalSchedule extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long hospitalScheduleSeq;

	@ManyToOne
	@JoinColumn(name = "hospital_seq")
	private Hospital hospital;

	@Column(nullable = false)
	private Date days;  // 0: 월요일 ~ 6: 일요일

	@Column(nullable = false)
	private String openingTime;

	@Column(nullable = false)
	private String closingTime;
}
