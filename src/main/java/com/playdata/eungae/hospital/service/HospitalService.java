package com.playdata.eungae.hospital.service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.dto.HospitalRegisterRequestDto;
import com.playdata.eungae.hospital.dto.HospitalSearchResponseDto;
import com.playdata.eungae.hospital.dto.HospitalViewResponseDto;
import com.playdata.eungae.hospital.dto.KeywordSearchRequestDto;
import com.playdata.eungae.hospital.repository.HospitalRepository;
import com.playdata.eungae.hospital.repository.HospitalScheduleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HospitalService {
	private static final double MAX_DISTANCE_KM = 3;
	private static final double EARTH_RADIUS_KM = 6371;
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

  @Transactional(readOnly = true)
	public List<HospitalSearchResponseDto> findAllNearbyHospital(double longitude, double latitude) {
		List<Hospital> hospitalList = hospitalRepository.findAll();
		List<HospitalSearchResponseDto> nearbyHospitalList = hospitalList.stream()
			.filter(
				hospital ->
					calculateDistance(latitude, longitude, hospital.getYCoordinate(), hospital.getXCoordinate())
						< MAX_DISTANCE_KM)
			.sorted(
				Comparator.comparing(
					hospital ->
						calculateDistance(latitude, longitude, hospital.getYCoordinate(), hospital.getXCoordinate())))
			.map(HospitalSearchResponseDto::toDto).toList();
		if (nearbyHospitalList.isEmpty()) {
			throw new NoSuchElementException("There's no hospital nearby");
		}
		return nearbyHospitalList;
	}

	@Transactional(readOnly = true)
	public List<HospitalSearchResponseDto> findAllByKeyword(String keyword) {
		List<Hospital> hospitalsByKeyword = hospitalRepository.findAllByKeyword(keyword);

		return hospitalsByKeyword.stream()
			.map(HospitalSearchResponseDto::toDto)
			.toList();
}

 @Transactional(readOnly = true)
	public List<HospitalSearchResponseDto> getAllHospitalByKeyword(KeywordSearchRequestDto keywordDto) {
		List<Hospital> hospitalsByKeyword = hospitalRepository.findAllByKeyword(keywordDto.getKeyword());
		List<HospitalSearchResponseDto> hospitalSearchResults;
		// 위치정보가 있으면 거리순으로 정렬
		if (keywordDto.hasLocationInfo()) {
			hospitalSearchResults = hospitalsByKeyword.stream()
				.sorted(
					Comparator.comparing(
						hospital ->
							calculateDistance(
								keywordDto.getLatitude(), keywordDto.getLongitude(),
								hospital.getYCoordinate(), hospital.getXCoordinate())))
				.map(HospitalSearchResponseDto::toDto)
				.toList();
		} else {
			hospitalSearchResults = hospitalsByKeyword.stream()
				.map(HospitalSearchResponseDto::toDto)
				.toList();
		}
		return hospitalSearchResults;
	}

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		// 현재 lat, 현재 lon, 목적지 lat, 목적지 lon
		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
		lon2 = Math.toRadians(lon2);

		return EARTH_RADIUS_KM * Math.acos(Math.sin(lon1) * Math.sin(lon2)
			+ Math.cos(lon1) * Math.cos(lon2) * Math.cos(lat1 - lat2));
	}
}
