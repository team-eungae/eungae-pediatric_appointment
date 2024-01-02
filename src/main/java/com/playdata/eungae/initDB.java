package com.playdata.eungae;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.member.domain.Member;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class initDB {

	private final InitService initService;

	@PostConstruct
	public void init() {
		// 이곳에 정의한 메소드를 추가해주시면 됩니다.
		initService.dbInitMember();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService {

		private final EntityManager em;

		public void dbInitMember() {

			// Entity 객체를 생성하고 em.persist해주면 스프링 컨테이너가 실행될때 해당 값이 db에 들어갑니다.
			// 주의해야할 점은 최초로 스프링 컨테이너를 실행시킨 뒤 DDL create 옵션을 꺼줘야합니다.
			// Member member = Member.createMember()
			// em.persist(member);

		}

	}
}
