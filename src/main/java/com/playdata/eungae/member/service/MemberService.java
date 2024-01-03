package com.playdata.eungae.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.repository.HospitalRepository;
import com.playdata.eungae.member.domain.FavoritesHospital;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.RequestFavoriesDto;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.repository.FavoritesHospitalRepository;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
// @Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final HospitalRepository hospitalRepository;
	private final FavoritesHospitalRepository favoritesHospitalRepository;

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
		FavoritesHospital favoritesHospital = FavoritesHospital.set(result.member, result.hospital);

		// Cascade 옵션을 사용하는 것보다 Repository를 모두 호출하는것이 안전하다고 판단되어 해당 방법을 채택했습니다.
		favoritesHospitalRepository.save(favoritesHospital);
		hospitalRepository.save(result.hospital);
		memberRepository.save(result.member);
	}

	@Transactional
	public void removeFavorites(RequestFavoriesDto requestFavoriesDto) {
		Result result = getMemberAndHospital(requestFavoriesDto);
		FavoritesHospital favoritesHospital = FavoritesHospital.removeFavoritesHospital(result.member, result.hospital);

		// Cascade 옵션을 사용하는 것보다 Repository를 모두 호출하는것이 안전하다고 판단되어 해당 방법을 채택했습니다.
		memberRepository.save(result.member);
		hospitalRepository.save(result.hospital);
		favoritesHospitalRepository.delete(favoritesHospital);
	}

	private Result getMemberAndHospital(RequestFavoriesDto requestFavoriesDto) {

		Member member = memberRepository.findById(requestFavoriesDto.getMemberSeq())
			.orElseThrow(() -> new IllegalStateException("Item Not Found"));

		Hospital hospital = hospitalRepository.findById(requestFavoriesDto.getHospitalSeq())
			.orElseThrow(() -> new IllegalStateException("Item Not Found"));

		return new Result(member, hospital);
	}

	// https://scshim.tistory.com/372 record에 대한 설명
	// record가 가변객체로 생성되는 문제를 해결할 수 있는 방법에 대해 생각해봐야 할 것 같습니다.
	private record Result(Member member, Hospital hospital) {
	}
}
