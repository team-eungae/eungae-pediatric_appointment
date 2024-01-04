package com.playdata.eungae.doctor.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.doctor.dto.DoctorViewResponseDto;
import com.playdata.eungae.doctor.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DoctorService {
	private final DoctorRepository doctorRepository;

	public List<DoctorViewResponseDto> findDoctorsByHospitalId(Long hospitalSeq){
		
		List<Doctor> doctorList = doctorRepository.findAllByHospitalHospitalSeq(hospitalSeq);
		
		return doctorList.stream()
			.map(DoctorViewResponseDto::toDto)
			.collect(Collectors.toList());
	}

}
