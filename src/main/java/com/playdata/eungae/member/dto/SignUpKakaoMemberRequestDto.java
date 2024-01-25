package com.playdata.eungae.member.dto;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.playdata.eungae.member.domain.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpKakaoMemberRequestDto {

	private Long kakaoMemberId;

	@NotBlank
	@Email
	private String email;

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

	public static Member toEntity(SignUpKakaoMemberRequestDto signUpMemberRequestDto) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = UUID.randomUUID().toString();

		String encodePassword = passwordEncoder.encode(password);

		return Member.builder()
			.email(signUpMemberRequestDto.getEmail())
			.name(signUpMemberRequestDto.getName())
			.password(encodePassword)
			.phoneNumber(signUpMemberRequestDto.getPhoneNumber())
			.birthDate(formattedBirthDate(signUpMemberRequestDto.getBirthDate()))
			.address(signUpMemberRequestDto.getAddress())
			.addressDetail(signUpMemberRequestDto.getAddressDetail())
			.zipCode(signUpMemberRequestDto.getZipCode())
			.build();
	}

	private static String formattedBirthDate(String birthDate) {
		return birthDate.substring(0, 4) + "-" + birthDate.substring(4, 6) + "-" + birthDate.substring(6);
	}

}
