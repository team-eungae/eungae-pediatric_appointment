package com.playdata.eungae.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.repository.HospitalRepository;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.RequestFavoriesDto;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final HospitalRepository hospitalRepository;

	/**
	 * 회원가입
	 * @return 회원가입에 성공한 유저의 식별자
	 */
	@Transactional
	public Member singUp(SignUpMemberRequestDto signUpMemberRequestDto) {
		return memberRepository.save(SignUpMemberRequestDto.toEntity(signUpMemberRequestDto));
	}

	@Transactional
	public void appendFavorites(RequestFavoriesDto requestFavoriesDto) {
		Result result = getMemberAndHospital(requestFavoriesDto);
		result.member().setHospitals(result.hospital());
	}

	@Transactional
	public void removeFavorites(RequestFavoriesDto requestFavoriesDto) {
		Result result = getMemberAndHospital(requestFavoriesDto);
		result.member().remove(result.hospital());
	}

	private Result getMemberAndHospital(RequestFavoriesDto requestFavoriesDto) {
		Member member = memberRepository.findById(requestFavoriesDto.getMemberSeq())
			.orElseThrow(() -> new IllegalStateException("회원 정보를 찾을 수 없습니다."));
		Hospital hospital = hospitalRepository.findById(requestFavoriesDto.getHospitalSeq())
			.orElseThrow(() -> new IllegalStateException("병원 정보를 찾을 수 없습니다."));
		return new Result(member, hospital);
	}

	// https://scshim.tistory.com/372 record에 대한 설명
	private record Result(Member member, Hospital hospital) {
	}
}
