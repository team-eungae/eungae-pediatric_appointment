package com.playdata.eungae.security;

import java.util.UUID;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberOAuthUserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		OAuthUserInfo oAuth2UserInfo = null;
		String provider = userRequest.getClientRegistration().getRegistrationId();

		if (provider.equals("kakao")) {
			oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
		}

		String email = oAuth2UserInfo != null ? oAuth2UserInfo.getEmail() : null;
		Member member = email != null ? memberRepository.findByEmail(email).orElse(null) : null;

		if (member == null) {
			member = Member.builder()
				.name(oAuth2UserInfo != null ? oAuth2UserInfo.getName() : null)
				.email(email)
				.provider(provider)
				.providerId(oAuth2UserInfo != null ? oAuth2UserInfo.getProviderId() : null)
				.address("정보를 입력해 주세요.")
				.zipCode("정보를 입력해 주세요.")
				.password(UUID.randomUUID().toString())
				.birthDate("정보를 입력해 주세요.")
				.phoneNumber("정보를 입력해 주세요.")
				.build();

			// 이메일이 null이 아닐 때만 저장
			if (email != null) {
				memberRepository.save(member);
			}
		}


		return new MemberUserDetails(member, oAuth2UserInfo.getAttributes());
	}
}
