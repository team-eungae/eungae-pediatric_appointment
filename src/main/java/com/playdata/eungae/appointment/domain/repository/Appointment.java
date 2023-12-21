package com.playdata.eungae.appointment.domain.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Parent;

import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.doctor.domain.Doctor;

import ch.qos.logback.core.joran.action.AppenderRefAction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
