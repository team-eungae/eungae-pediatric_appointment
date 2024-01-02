package com.playdata.eungae.member.dto;

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

}
