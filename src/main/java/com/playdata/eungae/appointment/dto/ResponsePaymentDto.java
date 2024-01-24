package com.playdata.eungae.appointment.dto;

import com.playdata.eungae.appointment.domain.Appointment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponsePaymentDto {

	private Long appointmentSeq;
	private int deposit;
	private String email;
	private String name;
	private String phoneNumber;
	private String address;
	private String addressDetail;
	private String zipcode;
	private LocalDateTime createdAt;

	public static ResponsePaymentDto toDto(Appointment appointment) {
		return ResponsePaymentDto.builder()
			.appointmentSeq(appointment.getAppointmentSeq())
			.deposit(appointment.getHospital().getDeposit())
			.email(appointment.getMember().getEmail())
			.name(appointment.getMember().getName())
			.phoneNumber(appointment.getMember().getPhoneNumber())
			.address(appointment.getMember().getAddress())
			.addressDetail(appointment.getMember().getAddressDetail())
			.zipcode(appointment.getMember().getZipCode())
			.createdAt(appointment.getCreatedAt())
			.build();
	}
}
