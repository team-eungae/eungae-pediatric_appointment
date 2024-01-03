package com.playdata.eungae.member.service;

import com.playdata.eungae.member.dto.MemberFindResponseDto;
import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
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
	@Transactional
	public MemberUpdateResponseDto updateMember(MemberUpdateRequestDto updateRequestDto) {
		Member member = memberRepository.findByEmail(updateRequestDto.getEmail()).orElseThrow(null);

		// updateMemberDetails 메서드 호출
		member.updateMemberDetails(updateRequestDto);

		Member updatedMember = memberRepository.save(member);
		return MemberUpdateResponseDto.toDto(updatedMember);
	}

	public MemberFindResponseDto findById(Long memberSeq){
		Optional<Member> optionalMember = memberRepository.findById(memberSeq);
		if(optionalMember.isPresent()) {
			log.info(optionalMember.get().getName()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			return MemberFindResponseDto.toDto(optionalMember.get());
		} else {
			return null;
		}
	}

	public MemberUpdateResponseDto updateFindById(Long memberSeq){
		Optional<Member> optionalMember = memberRepository.findById(memberSeq);
		if(optionalMember.isPresent()) {
			log.info(optionalMember.get().getName()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			return MemberUpdateResponseDto.toDto(optionalMember.get());
		} else {
			return null;
		}
	}
}
