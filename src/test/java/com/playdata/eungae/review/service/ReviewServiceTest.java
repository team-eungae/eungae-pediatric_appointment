package com.playdata.eungae.review.service;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.review.domain.Review;
import com.playdata.eungae.review.repository.ReviewRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class ReviewServiceTest {

	@Autowired
	ReviewRepository reviewRepository;

	@PersistenceContext
	EntityManager em;

	@Test
	@DisplayName("리뷰_등록시_성공적으로_DB에_리뷰가_저장되는지_테스트")
	public void createReviewTest() throws Exception {
	    //given;
		Member member = Member.builder()
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


		Hospital hospital = Hospital.builder()
			.password("testpassword")
			.name("testname")
			.notice("test")
			.deposit(1000)
			.contact("testcontant")
			.address("test")
			.addressDetail("testDetail")
			.lunchTime(1000L)
			.lunchEndTime(1000L)
			.businessRegistration("test")
			.build();
		em.persist(hospital);

		Appointment appointment = Appointment.builder()
			.member(member)
			.hospital(hospital)
			.appointmentDate(new Date())
			.appointmentHour("10")
			.appointmentMinute("30")
			.status("1")
			.note("test")
			.build();
		em.persist(appointment);

		Review review = Review.builder()
			.member(member)
			.hospital(hospital)
			.appointment(appointment)
			.content("testcontent")
			.starRating(5)
			.build();

	    //when
		reviewRepository.save(review);

	    //then
		Assertions.assertThat(review).isEqualTo(reviewRepository.findAll().get(0));
	}


}