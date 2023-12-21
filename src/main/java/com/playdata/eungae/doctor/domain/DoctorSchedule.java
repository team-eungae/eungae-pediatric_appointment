package com.playdata.eungae.doctor.domain;

import com.playdata.eungae.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "doctor_schedule")
@Entity
public class DoctorSchedule extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long doctorScheduleSeq;

	@JoinColumn(name = "doctor_seq")
	@ManyToOne
	private Doctor doctor;

	@Column(nullable = false)
	private String days;

	@Column(nullable = false)
	private String openingTime;

	@Column(nullable = false)
	private String closingTime;
}
