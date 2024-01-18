package com.playdata.eungae.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

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

}
