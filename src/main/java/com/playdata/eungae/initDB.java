package com.playdata.eungae;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.review.domain.Review;

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
			Member member = getMember("test5@gmail.com");
			em.persist(member);
			Hospital hospital = getHospital();
			em.persist(hospital);
			// Hospital hospital = hospitalRepository.findById(1L).get();
			Appointment appointment = getAppointment(member, hospital);
			em.persist(appointment);
		}
	}

	private static Review getReview(Member member, Hospital hospital, Appointment appointment) {
		return Review.builder()
			.member(member)
			.hospital(hospital)
			.appointment(appointment)
			.content("testcontent")
			.starRating(5)
			.build();
	}

	private static Member getMember(String mail) {
		return Member.builder()
			.address("독산")
			.name("전병준")
			.addressDetail("플레이데이터")
			.birthDate("9/24")
			.email(mail)
			.password("$2a$10$eLEyqJU4M1Hzhwn9SU07ou5MZNwBLzd7mfgFD/scIEhIyu4ofFWmC") //aA12345!
			.phoneNumber("01011112222")
			.zipCode("112233")
			.build();
	}

	private static Hospital getHospital() {
		return Hospital.builder()
			.password("testpassword")
			.name("testname")
			.notice("test")
			.deposit(1000)
			.contact("testcontant")
			.address("test")
			.addressDetail("testDetail")
			// .lunchTime(1000L)
			// .lunchEndTime(1000L)
			.businessRegistration("test")
			.build();
	}

	private static Appointment getAppointment(Member member, Hospital hospital) {
		return Appointment.builder()
			.member(member)
			.hospital(hospital)
			.appointmentDate(LocalDateTime.now())
			.appointmentHour("10")
			.appointmentMinute("30")
			.status("1")
			.note("test")
			.build();
	}
}