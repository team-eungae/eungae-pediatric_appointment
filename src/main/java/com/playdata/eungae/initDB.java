package com.playdata.eungae;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentStatus;
import com.playdata.eungae.article.domain.Notice;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.domain.HospitalImage;
import com.playdata.eungae.hospital.domain.HospitalSchedule;
import com.playdata.eungae.hospital.service.HospitalImageService;
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
		private final HospitalImageService hospitalImageService;

		public void dbInitMember() {
			Member member = getMember("test5@gmail.com");
			em.persist(member);

			Hospital hospital = getHospital();
			em.persist(hospital);

			Doctor doctor1 = getDoctor(hospital);
			Doctor doctor2 = getDoctor(hospital);
			em.persist(doctor1);
			em.persist(doctor2);

			HospitalSchedule hospitalSchedule = getHospitalSchedule();
			hospitalSchedule.setHospital(hospital);
			em.persist(hospitalSchedule);

			Notice notice = getNotice();
			em.persist(notice);

			Appointment appointment1 = getAppointment(member, hospital, doctor1);
			Appointment appointment2 = getAppointment(member, hospital, doctor1);
			Appointment appointment3 = getAppointment(member, hospital, doctor1);
			em.persist(appointment1);
			em.persist(appointment2);
			em.persist(appointment3);

			Review review1 = getReview(member, hospital, appointment1);
			Review review2 = getReview(member, hospital, appointment2);
			Review review3 = getReview(member, hospital, appointment3);
			em.persist(review1);
			em.persist(review2);
			em.persist(review3);
		}
	}

	private static Review getReview(Member member, Hospital hospital, Appointment appointment) {
		return Review.builder()
			.member(member)
			.hospital(hospital)
			.appointment(appointment)
			.content("친절하시지만 빨리 낫는 지는 모르겠어요")
			.starRating(3)
			.build();
	}

	private static Member getMember(String mail) {
		return Member.builder()
			.address("서울 금천구 가산디지털1로 25")
			.name("전병준")
			.addressDetail("1801호")
			.birthDate("9/24")
			.email(mail)
			.password("$2a$10$eLEyqJU4M1Hzhwn9SU07ou5MZNwBLzd7mfgFD/scIEhIyu4ofFWmC") //aA12345!
			.phoneNumber("010-9655-1302")
			.zipCode("14281")
			.build();
	}

	private static Hospital getHospital() {
		return Hospital.builder()
			.password("testpassword")
			.name("새움소아과")
			.notice("15세 이상 오지 마세요.")
			.deposit(1000)
			.contact("1577-7015")
			.address("test")
			.xCoordinate(37.4729951)
			.yCoordinate(126.8976605)
			.address("서울 금천구 시흥대로 139길 8")
			.addressDetail("본관")
			.businessRegistration("test")
			.hospitalSchedule(getHospitalSchedule())
			.build();
	}

	private static HospitalSchedule getHospitalSchedule() {
		return HospitalSchedule.builder()
			.monOpen("0900")
			.monClose("1830")
			.tueOpen("0830")
			.tueClose("1830")
			.wedOpen("0830")
			.wedClose("1830")
			.thuOpen("0830")
			.thuClose("1830")
			.friOpen("0830")
			.friClose("1830")
			.satOpen("0830")
			.satClose("1830")
			.sunOpen("0830")
			.sunClose("1830")
			.lunchHour("1200")
			.lunchEndHour("1300")
			.build();
	}

	private static Hospital getHospital2() {
		return Hospital.builder()
			.password("testpassword2")
			.name("새움소아과 별관")
			.notice("그냥 오지 마세요.")
			.deposit(10000)
			.contact("1577-7015123")
			.address("test2")
			.xCoordinate(37.47190170505455)
			.yCoordinate(126.89769223068006)
			.address("서울 금천구 시흥대로 130길 8")
			.addressDetail("별관")
			.businessRegistration("test")
			.hospitalSchedule(getHospitalSchedule2())
			.build();
	}

	private static HospitalSchedule getHospitalSchedule2() {
		return HospitalSchedule.builder()
			.monOpen("0900")
			.monClose("1730")
			.tueOpen("0930")
			.tueClose("1830")
			.wedOpen("0830")
			.wedClose("1830")
			.thuOpen("0830")
			.thuClose("1830")
			.friOpen("0830")
			.friClose("1830")
			.satOpen("0830")
			.satClose("1830")
			.sunOpen("0830")
			.sunClose("1830")
			.lunchHour("1200")
			.lunchEndHour("1300")
			.build();
	}

	private static Appointment getAppointment(Member member, Hospital hospital, Doctor doctor) {
		return Appointment.builder()
			.member(member)
			.hospital(hospital)
			.doctor(doctor)
			.appointmentDate(LocalDate.now())
			.appointmentHHMM("1030")
			.status(AppointmentStatus.APPOINTMENT)
			.note("test")
			.build();
	}

	private static Notice getNotice() {
		return Notice.builder()
			.title("응애 시스템 점검")
			.content("응애 서비스 시스템 점검 예정 안내입니다. 1월 8일 05시부터 10시까지 서비스 점검 예정이오니 약간 알아서 예약 안되는거 아쇼")
			.build();
	}

		private static Doctor getDoctor (Hospital hospital){
			return Doctor.builder()
				.name("김우진")
				.hospital(hospital)
				.treatmentPossible(3)
				.profileImage("doctor.jpeg")
				.build();
		}

		private static HospitalImage getHospitalImage (Hospital hospital){
			return HospitalImage.builder()
				.hospital(hospital)
				.originFileName("")
				.storeFileName("")
				.build();
		}
	}