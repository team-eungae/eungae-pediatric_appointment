package com.playdata.eungae.doctor.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Doctor {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long doctorSeq;

	// @JoinColumn(name = "hospital_seq")
	// @ManyToOne
	// private Hospital hospital_seq;

	@Column(nullable = false)
	private String name;

	@ColumnDefault("N")
	private String status;

	@Column(nullable = false)
	private Integer treatmentPossible; // 30분당 진료 가능 환자

	@Column(nullable = false)
	private String profileImage;

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;
}
