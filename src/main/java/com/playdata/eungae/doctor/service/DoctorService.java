package com.playdata.eungae.doctor.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.doctor.dto.DoctorRegisterRequestDto;
import com.playdata.eungae.doctor.dto.DoctorResponseDto;
import com.playdata.eungae.doctor.repository.DoctorRepository;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DoctorService {
	private final DoctorRepository doctorRepository;
	private final HospitalRepository hospitalRepository;

	@Transactional(readOnly = true)
	public List<DoctorResponseDto> findDoctorsByHospitalSeq(Long hospitalSeq) {

		List<Doctor> doctorList = doctorRepository.findAllByHospitalHospitalSeq(hospitalSeq);

		return doctorList.stream()
			.map(DoctorResponseDto::toDto)
			.collect(Collectors.toList());
	}

	@Transactional
	public void createDoctor(DoctorRegisterRequestDto doctorRegisterRequestDto, String hospitalId) {
		Doctor doctor = doctorRegisterRequestDto.toEntity(doctorRegisterRequestDto);

		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId)
			.orElseThrow(
				() -> new NoSuchElementException("Hospital not found by hospitalId = {%s}".formatted(hospitalId)));

		doctor.setHospital(hospital);

		doctorRepository.save(doctor);
	}

}
