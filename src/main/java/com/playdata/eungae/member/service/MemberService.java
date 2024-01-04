package com.playdata.eungae.member.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.repository.HospitalRepository;
import com.playdata.eungae.member.domain.FavoritesHospital;
import com.playdata.eungae.member.domain.Member;

import com.playdata.eungae.member.dto.RequestFavoriesDto;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.repository.FavoritesHospitalRepository;

import com.playdata.eungae.member.dto.MemberFindResponseDto;
import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;

import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final HospitalRepository hospitalRepository;
	private final FavoritesHospitalRepository favoritesHospitalRepository;

	@Transactional
	public void appendFavorites(RequestFavoriesDto requestFavoriesDto) {
		Result result = getMemberAndHospital(requestFavoriesDto);
		FavoritesHospital favoritesHospital = settingRelation(result.member, result.hospital);

		favoritesHospitalRepository.save(favoritesHospital);
		hospitalRepository.save(result.hospital);
		memberRepository.save(result.member);
	}

	@Transactional
	public void removeFavorites(RequestFavoriesDto requestFavoriesDto) {
		Result result = getMemberAndHospital(requestFavoriesDto);
		FavoritesHospital favoritesHospital = removeFavoritesHospital(result.member, result.hospital);

		memberRepository.save(result.member);
		hospitalRepository.save(result.hospital);
		favoritesHospitalRepository.delete(favoritesHospital);
	}

	@Transactional
	public Member savedMember(Member member) {
		validateDuplicateMemberEmail(member);
		return memberRepository.save(member);
	}

	@Transactional
	public MemberUpdateResponseDto updateMember(MemberUpdateRequestDto updateRequestDto) {
		Member member = memberRepository.findByEmail(updateRequestDto.getEmail()).orElseThrow(null);

		member.updateMemberDetails(updateRequestDto);

		Member updatedMember = memberRepository.save(member);
		return MemberUpdateResponseDto.toDto(updatedMember);
	}

	public MemberFindResponseDto findById(Long memberSeq) {
		Optional<Member> optionalMember = memberRepository.findById(memberSeq);
		return optionalMember.map(MemberFindResponseDto::toDto).orElse(null);
	}

	public MemberUpdateResponseDto updateFindById(Long memberSeq) {
		Optional<Member> optionalMember = memberRepository.findById(memberSeq);
		return optionalMember.map(MemberUpdateResponseDto::toDto).orElse(null);
	}

	private void validateDuplicateMemberEmail(Member member) {
		Optional<Member> findMemberByEmail = memberRepository.findByEmail(member.getEmail());
		if (findMemberByEmail.isPresent()) {
			throw new IllegalStateException("이미 가입된 이메일입니다.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<Member> member = memberRepository.findByEmail(email);

		if (member.isEmpty()) {
			throw new UsernameNotFoundException(email);
		}

		return User.builder()
			.username(String.valueOf(member.get().getMemberSeq()))
			.password(member.get().getPassword())
			.build();
	}
  
  private Result getMemberAndHospital(RequestFavoriesDto requestFavoriesDto) {

		Member member = memberRepository.findById(requestFavoriesDto.getMemberSeq())
			.orElseThrow(() -> new IllegalStateException("Item Not Found"));

		Hospital hospital = hospitalRepository.findById(requestFavoriesDto.getHospitalSeq())
			.orElseThrow(() -> new IllegalStateException("Item Not Found"));

		return new Result(member, hospital);
	}

	// 연관관계 편의 메서드
	private static FavoritesHospital settingRelation(Member member, Hospital hospital) {
		FavoritesHospital favoritesHospital = FavoritesHospital.builder()
			.member(member)
			.hospital(hospital)
			.build();
		member.getFavoritesHospitals().add(favoritesHospital);
		hospital.getFavoritesHospitals().add(favoritesHospital);
		return favoritesHospital;
	}

	// ManyToMany를 삭제하기 위한 연관관계 정리 메서드
	private static FavoritesHospital removeFavoritesHospital(Member member, Hospital hospital) {
		FavoritesHospital favoritesHospital = member.getFavoritesHospitals()
			.stream()
			.filter((entity) -> entity.getHospital()
				.equals(hospital))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("Item Not Found"));
		favoritesHospital.setMember(null);
		favoritesHospital.setHospital(null);
		member.getFavoritesHospitals().remove(favoritesHospital);
		hospital.getFavoritesHospitals().remove(favoritesHospital);
		return favoritesHospital;
	}

	private record Result(Member member, Hospital hospital) {}
}
