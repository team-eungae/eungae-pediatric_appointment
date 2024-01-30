package com.playdata.eungae.appointment.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Children;
import com.playdata.eungae.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@DynamicInsert
@Getter
@Table(name = "appointment")
@Builder
@Entity
public class Appointment extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long appointmentSeq;

	@JoinColumn(name = "member_seq")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@JoinColumn(name = "children_seq")
	@ManyToOne(fetch = FetchType.LAZY)
	private Children children;

	@JoinColumn(name = "doctor_seq")
	@ManyToOne(fetch = FetchType.LAZY)
	private Doctor doctor;

	@JoinColumn(name = "hospital_seq")
	@ManyToOne(fetch = FetchType.LAZY)
	private Hospital hospital;

	@Builder.Default
	@OneToMany(mappedBy = "appointment")
	private List<AppointmentDocument> appointmentDocuments = new ArrayList<>();

	@Column(nullable = false)
	private LocalDate appointmentDate;

	@Column/*(nullable = false)*/
	private String appointmentHHMM;

	@Setter
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;

	@Setter
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private AppointmentSort sort = AppointmentSort.EUNGAE;

	@Column
	private String note;

	// join seq
	@Setter
	private Long reviewSeq;
}
