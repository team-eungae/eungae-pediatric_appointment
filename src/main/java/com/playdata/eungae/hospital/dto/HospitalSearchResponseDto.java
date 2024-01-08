package com.playdata.eungae.hospital.dto;

import java.util.List;

import com.playdata.eungae.doctor.dto.DoctorViewResponseDto;
import com.playdata.eungae.hospital.domain.Hospital;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalSearchResponseDto {
	private Long hospitalSeq;
	private String name;
	private String notice;
	private int deposit; //예약금
	private String contact; //연락처
	private String address;
	private String addressDetail;

	public static HospitalSearchResponseDto toDto(Hospital entity){
		return HospitalSearchResponseDto.builder()
			.hospitalSeq(entity.getHospitalSeq())
			.name(entity.getName())
			.notice(entity.getNotice())
			.deposit(entity.getDeposit())
			.contact(entity.getContact())
			.address(entity.getAddress())
			.addressDetail(entity.getAddressDetail())
			.build();
	}
}
