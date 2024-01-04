package com.playdata.eungae.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.hospital.domain.Hospital;

import com.playdata.eungae.member.dto.RequestFavoriesDto;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.SignUpMemberRequestDto;
import com.playdata.eungae.member.repository.MemberRepository;

@Transactional
@SpringBootTest
class MemberServiceTest {

	@PersistenceContext
	EntityManager em;

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	@Rollback(value = false)
	@DisplayName("즐겨찾기 추가 api 요청을 받으면 Member.hospitals에 entity가 추가되는지 테스트")
	public void appendFavorites() throws Exception {
	    //given
		RequestFavoriesDto requestFavoriesDto = new RequestFavoriesDto(1L, 1L);

	    //when
		memberService.appendFavorites(requestFavoriesDto);

	    //then
		List<Hospital> hospitals = memberRepository.findById(1L).get().getHospitals();
		for (Hospital hospital : hospitals) {
			System.out.println("hospital = " + hospital.getName());
		}
	}

	@Test
	@DisplayName("즐겨찾기 삭제 api 요청을 받으면 Member.hospitals에 entity가 삭제되는지 테스트")
	public void removeFavorites() throws Exception {

		//given
		Member member = Member.builder()
			// .hospitals(hospitals)
			.address("독산")
			.name("전병준")
			.addressDetail("플레이데이터")
			.birthDate("9/24")
			.email("testId@gmail.com")
			.password("testpassword")
			.phoneNumber("01011112222")
			.zipCode("112233")
			.build();
		em.persist(member);

		Hospital hospital1 = Hospital.builder()
			.password("testpassword")
			.name("testname1")
			.notice("test")
			.deposit(1000)
			.contact("testcontant")
			.address("test")
			.addressDetail("testDetail")
			.lunchTime(1000L)
			.lunchEndTime(1000L)
			.businessRegistration("test")
			.build();
		em.persist(hospital1);

		Hospital hospital2 = Hospital.builder()
			.password("testpassword")
			.name("testname2")
			.notice("test")
			.deposit(1000)
			.contact("testcontant")
			.address("test")
			.addressDetail("testDetail")
			.lunchTime(1000L)
			.lunchEndTime(1000L)
			.businessRegistration("test")
			.build();
		em.persist(hospital2);

		RequestFavoriesDto requestFavoriesDto = new RequestFavoriesDto(1L, 1L);

		//when
		Member member1 = memberRepository.findById(member.getMemberSeq())
			.orElseThrow(() -> new IllegalStateException("멤버를 찾을 수 없음"));
		member1.setHospitals(hospital1, hospital2);

		em.flush();
		em.clear();

		Member member2 = memberRepository.findById(1L)
			.orElseThrow(() -> new IllegalStateException("멤버를 찾을 수 없음"));
		assertThat((long)member2.getHospitals().size()).isEqualTo(2);

		memberService.removeFavorites(requestFavoriesDto);

		Member member3 = memberRepository.findById(member.getMemberSeq())
			.orElseThrow(() -> new IllegalStateException("멤버를 찾을 수 없음"));

		//then
		assertThat((long)member2.getHospitals().size()).isEqualTo(1);


	}

	// @BeforeEach
	public Member createMember() {
		SignUpMemberRequestDto dto = new SignUpMemberRequestDto();
		dto.setName("김수용");
		dto.setEmail("test@gmail.com");
		dto.setAddress("경기도 안양시 만안구");
		dto.setAddressDetail("소곡로 26번길");
		dto.setZipCode("11033");
		dto.setBirthDate("19991213");
		dto.setPhoneNumber("01032626945");
		Member member1 = SignUpMemberRequestDto.toEntity(dto, passwordEncoder);

		return memberService.savedMember(member1);
	}

	@Rollback(value = false)
	@DisplayName("signUp: 회원가입을 성공한다.")
	@Test
	public void signUp() throws Exception {
		//Given
		SignUpMemberRequestDto dto = new SignUpMemberRequestDto();
		dto.setName("김수용");
		dto.setEmail("test@gmail.com");
		dto.setPassword("12345");
		dto.setAddress("경기도 안양시 만안구");
		dto.setAddressDetail("소곡로 26번길");
		dto.setZipCode("11033");
		dto.setBirthDate("19991213");
		dto.setPhoneNumber("01032626945");
		Member member = SignUpMemberRequestDto.toEntity(dto, passwordEncoder);

		//When
		memberService.savedMember(member);

		//Then
		assertThat(dto.getName()).isEqualTo(member.getName());
		System.out.println(member.getPassword());
	}

	@DisplayName("duplicateSignUp: 중복 회원가입은 예외를 발생시키며 가입되면 안된다.")
	@Test
	public void duplicateSignUp() throws Exception {
		//Given
		SignUpMemberRequestDto dto = new SignUpMemberRequestDto();
		dto.setName("김철수");
		dto.setEmail("test@gmail.com");
		dto.setPassword("12345");
		dto.setAddress("경기도 안양시 만안구");
		dto.setAddressDetail("소곡로 26번길");
		dto.setZipCode("11033");
		dto.setBirthDate("19991213");
		dto.setPhoneNumber("01032314233");
		Member member = SignUpMemberRequestDto.toEntity(dto, passwordEncoder);
		Member member2 = SignUpMemberRequestDto.toEntity(dto, passwordEncoder);
		memberService.savedMember(member2);
		//When
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
			memberService.savedMember(member);
		});

		//Then
		assertEquals("이미 가입된 이메일입니다.", e.getMessage());
	}
}
