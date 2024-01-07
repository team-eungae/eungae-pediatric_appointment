package com.playdata.eungae.hospital.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.dto.HospitalRegisterRequestDto;
import com.playdata.eungae.hospital.dto.HospitalSearchResponseDto;
import com.playdata.eungae.hospital.dto.HospitalViewResponseDto;
import com.playdata.eungae.hospital.repository.HospitalRepository;
import com.playdata.eungae.hospital.repository.HospitalScheduleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HospitalService {
	private final HospitalRepository hospitalRepository;
	private final HospitalScheduleRepository hospitalScheduleRepository;

	@Transactional
	public void saveHospital(HospitalRegisterRequestDto dto) {
		Hospital entity = HospitalRegisterRequestDto.toEntity(dto);
		hospitalRepository.save(entity);
		hospitalScheduleRepository.save(entity.getHospitalSchedule());
	}

	@Transactional(readOnly = true)
	public HospitalViewResponseDto findHospitalById(Long hospitalSeq) {
		Hospital hospital = hospitalRepository.findById(hospitalSeq)
			.orElseThrow(() -> new NoSuchElementException("Hospital not found"));

		return HospitalViewResponseDto.toDto(hospital);
	}

	public List<HospitalSearchResponseDto> findNearbyHospital(double latitude, double longitude) {
		List<Hospital> hospitalList = hospitalRepository.findAll();
		List<HospitalSearchResponseDto> nearbyHospitalList = hospitalList.stream()
			.filter(
				hospital ->
					calculateDistance(latitude, longitude, hospital.getXCoordinate(), hospital.getYCoordinate()) > 3)
			.map(HospitalSearchResponseDto::toDto).toList();
		if (nearbyHospitalList.isEmpty()) {
			throw new NoSuchElementException("There's no hospital nearby");
		}
		return nearbyHospitalList;
	}

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		// 현재 lat, 현재 lon, 목적지 lat, 목적지 lon
		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
		lon2 = Math.toRadians(lon2);

		double earthRadius = 6371; //Kilometers - 지구의 반지름
		return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2)
			+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
	}
}
