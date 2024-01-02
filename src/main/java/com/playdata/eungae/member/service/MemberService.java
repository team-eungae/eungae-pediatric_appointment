package com.playdata.eungae.member.service;

import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	/**
	 * 회원가입
	 * @return 회원가입에 성공한 유저의 식별자
	 */
	public Long singUp(SignUpMemberRequestDto signUpMemberRequestDto) {
		return memberRepository.save(Member.builder()
			.email(signUpMemberRequestDto.getEmail())
			.password(signUpMemberRequestDto.getPassword())
			.name(signUpMemberRequestDto.getName())
			.phoneNumber(signUpMemberRequestDto.getPhoneNumber())
			.birthDate(signUpMemberRequestDto.getBirthDate())
			.address(signUpMemberRequestDto.getAddress())
			.addressDetail(signUpMemberRequestDto.getAddressDetail())
			.zipCode(signUpMemberRequestDto.getZipCode())
			.build()).getMemberSeq();
	}

	//회원정보수정
	public MemberUpdateResponseDto updateMember(MemberUpdateRequestDto updateRequestDto) {
		Member member = memberRepository.findByEmail(updateRequestDto.getEmail()).orElseThrow(null);

		// updateMemberDetails 메서드 호출
		member.updateMemberDetails(updateRequestDto);

		Member updatedMember = memberRepository.save(member);
		return MemberUpdateResponseDto.toDto(updatedMember);
	}

	public MemberUpdateResponseDto findById(Long memberSeq){
		Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new NullPointerException());
		MemberUpdateResponseDto memberUpdateResponseDto = MemberUpdateResponseDto.toDto(member);
		return memberUpdateResponseDto;
	}
}
