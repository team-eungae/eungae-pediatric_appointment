package com.playdata.eungae.member.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.playdata.eungae.member.domain.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SignUpMemberRequestDto {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
	private String password;

	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;

	@NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
	private String phoneNumber;

	@NotBlank(message = "생년월일은 필수 입력 값입니다.")
	private String birthDate;

	@NotBlank(message = "주소 필수 입력 값입니다.")
	private String address;

	private String addressDetail;

	@NotBlank(message = "우편번호 필수 입력 값입니다.")
	private String zipCode;

	public static Member toEntity(SignUpMemberRequestDto signUpMemberRequestDto, PasswordEncoder passwordEncoder) {
		String password = passwordEncoder.encode(signUpMemberRequestDto.getPassword());

		return Member.builder()
			.email(signUpMemberRequestDto.getEmail())
			.password(password)
			.name(signUpMemberRequestDto.getName())
			.phoneNumber(signUpMemberRequestDto.getPhoneNumber())
			.birthDate(signUpMemberRequestDto.getBirthDate())
			.address(signUpMemberRequestDto.getAddress())
			.addressDetail(signUpMemberRequestDto.getAddressDetail())
			.zipCode(signUpMemberRequestDto.getZipCode())
			.build();
	}
}
