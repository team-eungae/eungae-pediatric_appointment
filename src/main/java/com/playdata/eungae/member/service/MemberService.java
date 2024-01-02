package com.playdata.eungae.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.classmate.MemberResolver;
import com.playdata.eungae.appointment.repository.AppointmentRepository;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.repository.HospitalRepository;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.RequestFavoriesDto;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final HospitalRepository hospitalRepository;

	/**
	 * 회원가입
	 * @return 회원가입에 성공한 유저의 식별자
	 */
	public Member singUp(SignUpMemberRequestDto signUpMemberRequestDto) {
		return memberRepository.save(SignUpMemberRequestDto.toEntity(signUpMemberRequestDto));
	}

	@Transactional
	public void appendFavorites(RequestFavoriesDto requestFavoriesDto) {
		Member member = memberRepository.findById(requestFavoriesDto.getMemberSeq())
			.orElseThrow(/* 발생할 Exception 생각해보기 */);
		Hospital hospital = hospitalRepository.findById(requestFavoriesDto.getHospitalSeq())
			.orElseThrow(/* 발생할 Exception 생각해보기 */);
		member.setHospitals(hospital);
	}
}
