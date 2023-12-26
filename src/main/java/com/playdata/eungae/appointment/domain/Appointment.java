package com.playdata.eungae.appointment.domain;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.member.domain.Children;
import com.playdata.eungae.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@DynamicInsert
@Getter
@Table(name = "appointment")
@Entity
public class Appointment extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long appointmentSeq;

	@JoinColumn(name = "member_seq")
	@ManyToOne
	private Member member;

	@JoinColumn(name = "children_seq")
	@ManyToOne
	private Children children;

	@JoinColumn(name = "doctor_seq")
	@ManyToOne
	private Doctor doctor;

	@Column(nullable = false)
	private Date appointmentDate;

	@Column(nullable = false)
	private String appointmentHour;

	@Column(nullable = false)
	private String appointmentMinute;

	@ColumnDefault("'0'")
	private String status;

	@Column
	private String note;
}
