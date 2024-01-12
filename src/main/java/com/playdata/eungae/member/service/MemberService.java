package com.playdata.eungae.member.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.playdata.eungae.member.dto.ResponseFavoritesHospitalDto;
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
    public MemberUpdateResponseDto updateMemberInfo(String email, MemberUpdateRequestDto updateRequestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("No member found for the email provided. email = {%s}".formatted(email)));
        member.updateMemberDetails(updateRequestDto);
        return MemberUpdateResponseDto.toDto(memberRepository.save(member));
    }

    @Transactional(readOnly = true)
    public MemberFindResponseDto findMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("The provided ID does not exist."));
        return MemberFindResponseDto.toDto(member);
    }

    @Transactional(readOnly = true)
    public MemberUpdateResponseDto updateMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("The provided ID does not exist."));
        return MemberUpdateResponseDto.toDto(member);
    }

    @Transactional(readOnly = true)
    public List<ResponseFavoritesHospitalDto> getFavoritesByMemberEmail(String userEmail) {
        List<FavoritesHospital> favoritesHospitals = favoritesHospitalRepository.getFavoritesHospitalListByUserEmail(userEmail);

        return favoritesHospitals.stream()
            .map(ResponseFavoritesHospitalDto::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean checkFavoriteStatus(Long hospitalSeq, String userEmail) {
        return !favoritesHospitalRepository.getFavoritesHospitalByUserEmail(userEmail, hospitalSeq).isEmpty();
    }


    @Transactional
    public void changeFavoriteStatus(Long hospitalSeq, String userEmail) {

        favoritesHospitalRepository.getFavoritesHospitalByUserEmail(userEmail, hospitalSeq)
            .ifPresentOrElse(
                favoritesHospital -> {
                    MemberAndHospitalEntity memberAndHospitalEntity = getMemberAndHospital(userEmail, hospitalSeq);
                    FavoritesHospital favoritesHospitalResult = removeFavoritesHospital(memberAndHospitalEntity.member, memberAndHospitalEntity.hospital);
                    saveEntities(favoritesHospitalResult, memberAndHospitalEntity);
                },
                () -> {
                    MemberAndHospitalEntity memberAndHospitalEntity = getMemberAndHospital(userEmail, hospitalSeq);
                    FavoritesHospital favoritesHospitalResult = settingRelation(memberAndHospitalEntity.member, memberAndHospitalEntity.hospital);
                    saveEntities(favoritesHospitalResult, memberAndHospitalEntity);
                }
            );
    }

    @Transactional
    public Member savedMember(Member member) {
        validateDuplicateMemberEmail(member);
        return memberRepository.save(member);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.get().getEmail())
                .password(member.get().getPassword())
                .build();
    }

    private MemberAndHospitalEntity getMemberAndHospital(String memberEmail, Long hospitalSeq) {

        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalStateException("Can Not Found Member Entity"));

        Hospital hospital = hospitalRepository.findById(hospitalSeq)
                .orElseThrow(() -> new IllegalStateException("Can Not Found Hospital Entity"));

        return new MemberAndHospitalEntity(member, hospital);
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
                .orElseThrow(() -> new IllegalStateException("No registered favorites found."));
        favoritesHospital.setMember(null);
        favoritesHospital.setHospital(null);
        member.getFavoritesHospitals().remove(favoritesHospital);
        hospital.getFavoritesHospitals().remove(favoritesHospital);
        return favoritesHospital;
    }

    private void saveEntities(FavoritesHospital favoritesHospitalResult, MemberAndHospitalEntity memberAndHospital) {
        favoritesHospitalRepository.save(favoritesHospitalResult);
        hospitalRepository.save(memberAndHospital.hospital);
        memberRepository.save(memberAndHospital.member);
    }

    private record MemberAndHospitalEntity(Member member, Hospital hospital) {
    }

    private void validateDuplicateMemberEmail(Member member) {
        Optional<Member> findMemberEmail = memberRepository.findByEmail(member.getEmail());
        if (findMemberEmail.isPresent()) {
            throw new IllegalStateException("이미 있는 이메일입니다.");
        }
    }
}