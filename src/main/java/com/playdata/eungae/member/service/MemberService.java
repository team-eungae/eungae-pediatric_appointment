package com.playdata.eungae.member.service;

import java.util.Optional;

import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.MemberFindResponseDto;
import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     *
     * @return 회원가입에 성공한 유저의 식별자
     */
    public Long singUp(SignUpMemberRequestDto signUpMemberRequestDto) {
        return memberRepository.save(Member.builder().email(signUpMemberRequestDto.getEmail()).password(signUpMemberRequestDto.getPassword()).name(signUpMemberRequestDto.getName()).phoneNumber(signUpMemberRequestDto.getPhoneNumber()).birthDate(signUpMemberRequestDto.getBirthDate()).address(signUpMemberRequestDto.getAddress()).addressDetail(signUpMemberRequestDto.getAddressDetail()).zipCode(signUpMemberRequestDto.getZipCode()).build()).getMemberSeq();
    }

    @Transactional
    public Member signUp(Member member) {
        validateDuplicateMemberEmail(member);
        return memberRepository.save(member);
    }

    @Transactional
    public MemberUpdateResponseDto updateMemberInfo(Long memberSeq, MemberUpdateRequestDto updateRequestDto) {
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
        member.updateMemberDetails(updateRequestDto);
        return MemberUpdateResponseDto.toDto(memberRepository.save(member));
    }

    @Transactional(readOnly = true)
    public MemberFindResponseDto findByMemberId(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
        return MemberFindResponseDto.toDto(member);
    }

    @Transactional(readOnly = true)
    public MemberUpdateResponseDto updateFindByMemberId(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
        return MemberUpdateResponseDto.toDto(member);
    }

    private void validateDuplicateMemberEmail(Member member) {
        Optional<Member> findMemberEmail = memberRepository.findByEmail(member.getEmail());
        if (findMemberEmail.isPresent()) {
            throw new IllegalStateException("이미 있는 이메일입니다.");
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
}
