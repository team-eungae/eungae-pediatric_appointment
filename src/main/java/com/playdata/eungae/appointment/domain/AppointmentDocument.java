package com.playdata.eungae.appointment.domain;

import com.playdata.eungae.base.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
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

	@ManyToOne
	@JoinColumn(name = "appointment_seq")
	private Appointment appointment;

	private String appointmentDocumentLoc;

	private String note;
}
