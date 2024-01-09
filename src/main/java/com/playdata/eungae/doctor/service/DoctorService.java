package com.playdata.eungae.doctor.service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.doctor.dto.DoctorRegisterRequestDto;
import com.playdata.eungae.doctor.dto.DoctorViewResponseDto;
import com.playdata.eungae.doctor.repository.DoctorRepository;
import com.playdata.eungae.hospital.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DoctorService {
	private final DoctorRepository doctorRepository;
	private final HospitalRepository hospitalRepository;

	public List<DoctorViewResponseDto> findDoctorsByHospitalId(Long hospitalSeq){
		
		List<Doctor> doctorList = doctorRepository.findAllByHospitalHospitalSeq(hospitalSeq)
			.orElseThrow(() -> new NoSuchElementException("Doctor not found"));
		
		return doctorList.stream()
			.map(DoctorViewResponseDto::toDto)
			.collect(Collectors.toList());
	}

	public void createDoctor(DoctorRegisterRequestDto doctorRegisterRequestDto){
		try {
			Doctor entity = doctorRegisterRequestDto.toEntity(doctorRegisterRequestDto);
			entity.setHospital(hospitalRepository.getReferenceById(1L));
			doctorRepository.save(entity);
		} catch (IOException e) {
			throw new RuntimeException("Doctor profile Image error");
		}
	}
}
