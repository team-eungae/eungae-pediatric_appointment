package com.playdata.eungae.hospital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.domain.HospitalSchedule;
import com.playdata.eungae.hospital.repository.HospitalRepository;

import jakarta.persistence.EntityManager;

@Transactional
@SpringBootTest
class HospitalServiceImplTest {
	@Autowired
	HospitalRepository hospitalRepository;
	@Autowired
	EntityManager em;

	@Test
	public void hospital_create_test() {

		List<Doctor> doctorList = new ArrayList<>();
		Doctor doctor1 = new Doctor("testdoctorname1");
		em.persist(doctor1);
		Doctor doctor2 = new Doctor("testdoctorname2");
		em.persist(doctor2);

		doctorList.add(doctor1);
		doctorList.add(doctor2);

		//
		// HospitalSchedule hospitalSchedule = new HospitalSchedule(
		// 	"1230","1330",
		// 	"0830","1830",
		// 	"0830","1830",
		// 	"0830","1830",
		// 	"0830","1830",
		// 	"0830","1830",
		// 	"0830","1830",
		// 	"0830","1830");
		// em.persist(hospitalSchedule);

		//
		// Hospital hospital = Hospital.buildHospital(hospitalSchedule,
		// 	"testpassword",
		// 	"testname",
		// 	"testnotice",
		// 	3000,
		// 	"02-111-2222",
		// 	"testaddress",
		// 	"testaddressdetail",
		// 	"testbusinessregistration",
		// 	34.56894,
		// 	127.568465);

		//
		// em.persist(hospital);
		//
		//
		//
		// //when
		// Hospital result =  hospitalRepository.save(hospital);
		//
		// //then
		// Assertions.assertThat(hospital).isEqualTo(hospitalRepository.findAll().get(0));

	}


}