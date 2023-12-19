package com.playdata.eungae.doctor.application;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor_closed")
public class DoctorClosed {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long hospitalClosedSeq;
	// @OneToOne
	// @JoinColumn(name = "hospital_seq")
	// private Hospital hospital;
	@Column(nullable = false)
	private LocalDateTime closedDay;
	private String reason;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
