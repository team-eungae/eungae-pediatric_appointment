package com.playdata.eungae.member.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Transactional
	public Member signUp(Member member) {
		validateDuplicateMemberEmail(member);
		return memberRepository.save(member);
	}

	private void validateDuplicateMemberEmail(Member member) {
		Member findMemberEmail = memberRepository.findByEmail(member.getEmail());
		if (findMemberEmail != null) {
			throw new IllegalStateException("이미 있는 이메일입니다.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Member member = memberRepository.findByEmail(email);

		if (member == null) {
			throw new UsernameNotFoundException(email);
		}

		return User.builder()
			.username(member.getEmail())
			.password(member.getPassword())
			.build();
	}

}
