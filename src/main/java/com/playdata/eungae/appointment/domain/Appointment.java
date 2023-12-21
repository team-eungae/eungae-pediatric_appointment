package com.playdata.eungae.appointment.domain;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.playdata.eungae.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@DynamicInsert
@Getter
@Table(name = "appointment")
@Entity
public class Appointment extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long appointmentSeq;

	// @JoinColumn(name = "parent_seq")
	// @ManyToOne
	// private Parent parent;

	// @JoinColumn(name = "children_seq")
	// @ManyToOne
	// private Children children;

	// @JoinColumn(name = "doctor_seq")
	// @ManyToOne
	// private Doctor doctor;

	@Column(nullable = false)
	private Date appointmentDate;

	@Column(nullable = false)
	private String appointmentHour;

	@Column(nullable = false)
	private String appointmentMinute;

	@ColumnDefault("''0")
	private String status;

	@Column
	private String note;

}
