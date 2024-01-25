package com.playdata.eungae.doctor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.doctor.domain.DoctorStatus;
import com.playdata.eungae.doctor.dto.DoctorResponseDto;
import com.playdata.eungae.doctor.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Transactional(readOnly = true)
    public List<DoctorResponseDto> findDoctorsByHospitalSeq(Long hospitalSeq) {

        List<Doctor> doctorList = doctorRepository.findAllByHospitalHospitalSeq(hospitalSeq, DoctorStatus.ON);

		return doctorList.stream()
				.map(DoctorResponseDto::toDto)
				.collect(Collectors.toList());
	}

}
