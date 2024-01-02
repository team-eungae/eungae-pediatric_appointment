package com.playdata.eungae.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public Long signUp(SignUpMemberRequestDto signUpMemberRequestDto, PasswordEncoder encoder) {
		return memberRepository.save(SignUpMemberRequestDto
				.toEntity(signUpMemberRequestDto, encoder))
			.getMemberSeq();
	}

}
