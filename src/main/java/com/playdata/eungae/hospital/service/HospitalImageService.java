package com.playdata.eungae.hospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.hospital.domain.HospitalImage;
import com.playdata.eungae.hospital.dto.HospitalImageResponseDto;
import com.playdata.eungae.hospital.repository.HospitalImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HospitalImageService {
	private final HospitalImageRepository hospitalImageRepository;

	@Transactional(readOnly = true)
	public List<HospitalImageResponseDto> findHospitalImages(Long hospitalSeq) {
		List<HospitalImage> hospitalImageList = hospitalImageRepository.findAllByHospitalHospitalSeq(hospitalSeq);

		return hospitalImageList.stream()
			.map(HospitalImageResponseDto::toDto)
			.collect(Collectors.toList());
	}
}
