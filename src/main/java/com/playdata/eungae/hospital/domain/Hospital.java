package com.playdata.eungae.hospital.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.doctor.domain.Doctor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "hospital")
@Entity
public class Hospital extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long hospitalSeq;

	@OneToMany(mappedBy = "hospital")
	private List<HospitalSchedule> hospitalSchedule = new ArrayList<>();

	@OneToMany(mappedBy = "hospital")
	private List<Doctor> doctor = new ArrayList<>();

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(length = 255)
	private String notice;

	@ColumnDefault("3000")
	private int deposit;

	@Column(nullable = false)
	private String contact;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String addressDetail;

	@Column(nullable = false)
	private String lunchStartTime;

	@Column(nullable = false)
	private String lunchEndTime;

	@Column(nullable = false)
	private String businessRegistration;

	@Column(nullable = false)
	private double latitude;

	@Column(nullable = false)
	private double longitude;

}