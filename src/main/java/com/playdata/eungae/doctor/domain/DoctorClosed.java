package com.playdata.eungae.doctor.domain;

import java.time.LocalDateTime;

import com.playdata.eungae.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "doctor_closed")
@Entity
public class DoctorClosed extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long doctorClosedSeq;

	@OneToOne
	@JoinColumn(name = "doctor_seq")
	private Doctor doctor;

	@Column(nullable = false)
	private LocalDateTime closedDay;

	private String reason;
}
