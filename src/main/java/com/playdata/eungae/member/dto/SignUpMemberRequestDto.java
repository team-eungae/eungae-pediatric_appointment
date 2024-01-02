package com.playdata.eungae.member.dto;

import com.playdata.eungae.member.domain.Member;

import lombok.Data;

@Data
public class SignUpMemberRequestDto {

	private String email;
	private String password;
	private String name;
	private String phoneNumber;
	private String birthDate;
	private String address;
	private String addressDetail;
	private String zipCode;

	public static Member toEntity(SignUpMemberRequestDto signUpMemberRequestDto) {
		return Member.builder()
			.email(signUpMemberRequestDto.getEmail())
			.password(signUpMemberRequestDto.getPassword())
			.name(signUpMemberRequestDto.getName())
			.phoneNumber(signUpMemberRequestDto.getPhoneNumber())
			.birthDate(signUpMemberRequestDto.getBirthDate())
			.address(signUpMemberRequestDto.getAddress())
			.addressDetail(signUpMemberRequestDto.getAddressDetail())
			.zipCode(signUpMemberRequestDto.getZipCode())
			.build();
	}



}
