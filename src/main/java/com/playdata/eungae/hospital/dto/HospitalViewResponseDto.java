package com.playdata.eungae.hospital.dto;

import java.util.List;

import com.playdata.eungae.doctor.domain.DoctorViewResponseDto;
import com.playdata.eungae.hospital.domain.Hospital;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalViewResponseDto {

	private Long hospitalSeq;
	private HospitalScheduleViewResponseDto hospitalSchedule;
	private List<DoctorViewResponseDto> doctor;
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

	public static Hospital toEntity(HospitalViewResponseDto dto) {
		return Hospital.builder()
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


	public static HospitalViewResponseDto toDto(Hospital entity) {
		return HospitalViewResponseDto.builder()
			.password(entity.getPassword())
			.name(entity.getName())
			.notice(entity.getNotice())
			.deposit(entity.getDeposit())
			.contact(entity.getContact())
			.address(entity.getAddress())
			.addressDetail(entity.getAddressDetail())
			.businessRegistration(entity.getBusinessRegistration())
			.xCoordinate(entity.getXCoordinate())
			.yCoordinate(entity.getYCoordinate())
			.build();
	}
}
