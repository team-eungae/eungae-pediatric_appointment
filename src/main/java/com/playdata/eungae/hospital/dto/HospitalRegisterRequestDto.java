package com.playdata.eungae.hospital.dto;

import com.playdata.eungae.hospital.domain.Hospital;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalRegisterRequestDto {
	private HospitalScheduleRequestDto scheduleRequestDto;
 	private String password;
	private String name;
	private String notice;
	private int deposit; //예약금
	private String contact; //연락처
	private String address;
	private String addressDetail;
	private String businessRegistration;
	private double xCoordinate;
	private double yCoordinate;

	public static Hospital toEntity(HospitalRegisterRequestDto dto) {
		return Hospital.builder()
			.hospitalSchedule(HospitalScheduleRequestDto.toEntity(dto.getScheduleRequestDto()))
			.password(dto.getPassword())
			.name(dto.getName())
			.notice(dto.getNotice())
			.deposit(dto.getDeposit())
			.contact(dto.getContact())
			.address(dto.getAddress())
			.addressDetail(dto.getAddressDetail())
			.businessRegistration(dto.getBusinessRegistration())
			.xCoordinate(dto.getXCoordinate())
			.yCoordinate(dto.getYCoordinate())
			.build();
	}
}
