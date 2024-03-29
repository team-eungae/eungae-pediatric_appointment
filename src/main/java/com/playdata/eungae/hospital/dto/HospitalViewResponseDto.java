package com.playdata.eungae.hospital.dto;

import java.util.List;

import com.playdata.eungae.doctor.dto.DoctorResponseDto;
import com.playdata.eungae.hospital.domain.Hospital;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalViewResponseDto {

	private Long hospitalSeq;
	private HospitalScheduleViewResponseDto hospitalSchedule;
	private List<DoctorResponseDto> doctor;
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

	public static HospitalViewResponseDto toDto(Hospital entity) {
		return HospitalViewResponseDto.builder()
			.hospitalSeq(entity.getHospitalSeq())
			.hospitalSchedule(HospitalScheduleViewResponseDto.toDto(entity.getHospitalSchedule()))
			.password(entity.getPassword())
			.name(entity.getName())
			.notice(entity.getNotice())
			.deposit(entity.getDeposit())
			.contact(entity.getContact())
			.address(entity.getAddress())
			.xCoordinate(entity.getXCoordinate())
			.yCoordinate(entity.getYCoordinate())
			.build();
	}
}
