package com.playdata.eungae.appointment.domain;

import com.playdata.eungae.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "appointment_document")
public class AppointmentDocument extends BaseEntity {

	@Id
	@GeneratedValue
	private Long appointmentDocumentSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointment_seq")
	private Appointment appointment;

	private String appointmentDocumentLoc;

	private String note;
}
