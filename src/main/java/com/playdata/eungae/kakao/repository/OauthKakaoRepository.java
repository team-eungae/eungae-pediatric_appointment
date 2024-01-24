package com.playdata.eungae.kakao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playdata.eungae.kakao.domain.OauthKakao;

public interface OauthKakaoRepository extends JpaRepository<OauthKakao, Long> {
	Optional<OauthKakao> findByKakaoMemberId(Long kakaoMemberId);
}
