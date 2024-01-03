package com.playdata.eungae.hospital.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.member.domain.FavoritesHospital;
import com.playdata.eungae.member.domain.Member;

import jakarta.persistence.CascadeType;
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
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "hospital")
@Entity
@Builder
public class Hospital extends BaseEntity {

	@Id
	@Column(name = "hospital_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long hospitalSeq;

	@OneToMany(mappedBy = "hospital")
	private List<HospitalSchedule> hospitalSchedule = new ArrayList<>();

	@Setter
	@OneToMany(mappedBy = "hospital")
	@Builder.Default
	private List<FavoritesHospital> favoritesHospitals = new ArrayList<>();

	@OneToMany(mappedBy = "hospital")
	private List<Appointment> appointments = new ArrayList<>();

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
	private Long lunchTime;

	@Column(nullable = false)
	private Long lunchEndTime;

	@Column(nullable = false)
	private String businessRegistration;

}