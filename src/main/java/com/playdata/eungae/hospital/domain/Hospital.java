package com.playdata.eungae.hospital.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.member.domain.FavoritesHospital;
import com.playdata.eungae.review.domain.Review;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Builder
@Table(name = "hospital")
@Entity
public class Hospital extends BaseEntity {

	@Id
	@Column(name = "hospital_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long hospitalSeq;

	@Column(name = "hospital_id")
	private String hospitalId;

	@Setter
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_schedule_seq")
	private HospitalSchedule hospitalSchedule;

	@OneToMany(mappedBy = "hospital")
	@Builder.Default
	private List<Doctor> doctorList = new ArrayList<>();

	@OneToMany(mappedBy = "hospital")
	@Builder.Default
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "hospital")
	@Builder.Default
	private List<HospitalImage> hospitalImageList = new ArrayList<>();

	@Setter
	@OneToMany(mappedBy = "hospital")
	@Builder.Default
	private List<FavoritesHospital> favoritesHospitals = new ArrayList<>();

	@OneToMany(mappedBy = "hospital")
	@Builder.Default
	private List<Appointment> appointments = new ArrayList<>();

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

	private double xCoordinate;

	private double yCoordinate;

	//연관관계 편의 메소드
	public void addDoctor(Doctor doctor) {
		this.doctorList.add(doctor);
	}

	public void addHospitalImage(HospitalImage hospitalImage) {
		this.hospitalImageList.add(hospitalImage);
	}
}