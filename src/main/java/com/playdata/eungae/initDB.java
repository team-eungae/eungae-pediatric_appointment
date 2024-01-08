package com.playdata.eungae;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentStatus;

import com.playdata.eungae.doctor.domain.Doctor;

import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.domain.HospitalSchedule;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.review.domain.Review;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class initDB {

	private final InitService initService;

	// @PostConstruct
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

			Hospital hospital2 = getHospital2();
			em.persist(hospital2);

			Doctor doctor = getDoctor();
			doctor.setHospital(hospital);
			em.persist(doctor);

			Doctor doctor2 = getDoctor2();
			doctor2.setHospital(hospital);
			em.persist(doctor2);

			HospitalSchedule hospitalSchedule = getHospitalSchedule();
			hospitalSchedule.setHospital(hospital);
			em.persist(hospitalSchedule);

			HospitalSchedule hospitalSchedule2 = getHospitalSchedule2();
			hospitalSchedule2.setHospital(hospital2);
			em.persist(hospitalSchedule2);

			Appointment appointment = getAppointment(member, hospital);
			em.persist(appointment);
		}
	}

	private static Doctor getDoctor() {
		return Doctor.builder()
			.name("전병준")
			.status("1")
			.treatmentPossible(3)
			.hospital(getHospital())
			.profileImage("")
			.build();
	}

	private static Doctor getDoctor2() {
		return Doctor.builder()
			.name("김우진")
			.status("1")
			.treatmentPossible(5)
			.hospital(getHospital())
			.profileImage("")
			.build();
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
			.name("새움소아과 본관")
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
			.tueOpen ("0830")
			.tueClose("1830")
			.wedOpen ("0830")
			.wedClose("1830")
			.thuOpen ("0830")
			.thuClose("1830")
			.friOpen ("0830")
			.friClose("1830")
			.satOpen ("0830")
			.satClose("1830")
			.sunOpen ("0830")
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
			.tueOpen ("0930")
			.tueClose("1830")
			.wedOpen ("0830")
			.wedClose("1830")
			.thuOpen ("0830")
			.thuClose("1830")
			.friOpen ("0830")
			.friClose("1830")
			.satOpen ("0830")
			.satClose("1830")
			.sunOpen ("0830")
			.sunClose("1830")
			.lunchHour("1200")
			.lunchEndHour("1300")
			.build();
	}

	private static Appointment getAppointment(Member member, Hospital hospital) {
		return Appointment.builder()
			.member(member)
			.hospital(hospital)
			.appointmentDate(LocalDateTime.now())
			.appointmentHour("10")
			.appointmentMinute("30")
			.status(AppointmentStatus.APPOINTMENT)
			.note("test")
			.build();
	}
}