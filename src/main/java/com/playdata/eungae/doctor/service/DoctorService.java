package com.playdata.eungae.doctor.service;

import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.doctor.dto.DoctorRegisterRequestDto;
import com.playdata.eungae.doctor.dto.DoctorResponseDto;
import com.playdata.eungae.doctor.repository.DoctorRepository;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Transactional(readOnly = true)
    public List<DoctorResponseDto> findDoctorsByHospitalSeq(Long hospitalSeq) {

        List<Doctor> doctorList = doctorRepository.findAllByHospitalHospitalSeq(hospitalSeq);

		return doctorList.stream()
				.map(DoctorResponseDto::toDto)
				.collect(Collectors.toList());
	}

}
