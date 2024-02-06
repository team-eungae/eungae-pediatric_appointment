package com.playdata.eungae.hospital.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playdata.eungae.appointment.repository.AppointmentRepository;
import com.playdata.eungae.hospital.domain.Hospital;
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
    private static final String HOSPITAL_HASH_KEY = "hospital";

    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;
    private final ObjectMapper objectMapper;
    private final HashOperations<String, String, String> hashOperations; // key, subKey, value 순서

    @Transactional(readOnly = true)
    public HospitalViewResponseDto findHospitalById(Long hospitalSeq) {
        Hospital hospital = hospitalRepository.findByHospitalSeq(hospitalSeq)
                .orElseThrow(() -> new NoSuchElementException("Hospital not found"));
        return HospitalViewResponseDto.toDto(hospital);
    }

    @Transactional(readOnly = true)
    public List<HospitalSearchResponseDto> getHospitalsNearby(double longitude, double latitude) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate currentDate = LocalDate.parse(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        List<HospitalSearchResponseDto> hospitalListFromRedis = getAllHospitalsFromRedis();
        if (hospitalListFromRedis.isEmpty()) {
            List<Hospital> hospitalList = hospitalRepository.findAll();
            return hospitalList.stream()
                    .map(HospitalSearchResponseDto::toDto)
                    .filter(hospital -> isHospitalInMaxDistance(latitude, longitude, hospital))
                    .sorted(
                            Comparator.comparing(
                                    hospital ->
                                            calculateDistance(latitude, longitude, hospital.getLatitude(), hospital.getLongitude())))
                    .peek(hospital -> {
                        hospital
                                .setCurrentWaitingCount(
                                        getCurrentAppointmentCount(hospital.getHospitalSeq(), now, currentDate));
                    }).toList();
        }
        return hospitalListFromRedis.stream().
                filter(hospital -> isHospitalInMaxDistance(latitude, longitude, hospital))
                .sorted(
                        Comparator.comparing(
                                hospital ->
                                        calculateDistance(latitude, longitude, hospital.getLatitude(), hospital.getLongitude())))
                .peek(hospital -> {
                    hospital
                            .setCurrentWaitingCount(
                                    getCurrentAppointmentCount(hospital.getHospitalSeq(), now, currentDate));
                }).toList();
    }

    @Transactional(readOnly = true)
    public List<HospitalSearchResponseDto> getHospitalsBy(KeywordSearchRequestDto keywordDto) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate currentDate = LocalDate.parse(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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
                    .filter(hospital -> {
                        return hospital.getHospitalId() != null;
                    })
                    .map(HospitalSearchResponseDto::toDto)
                    .peek(hospital -> {
                        hospital
                                .setCurrentWaitingCount(
                                        getCurrentAppointmentCount(hospital.getHospitalSeq(), now, currentDate));
                    }).toList();
        } else {
            hospitalSearchResults = hospitalsByKeyword.stream()
                    .map(HospitalSearchResponseDto::toDto)
                    .filter(hospital -> {
                        return hospital.getHospitalId() != null;
                    })
                    .peek(hospital -> {
                        hospital
                                .setCurrentWaitingCount(
                                        getCurrentAppointmentCount(hospital.getHospitalSeq(), now, currentDate));
                    }).toList();
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

    private boolean isHospitalInMaxDistance(double latitude, double longitude, HospitalSearchResponseDto hospitalDto) {
        return MAX_DISTANCE_KM > calculateDistance(
                latitude, longitude, hospitalDto.getLatitude(), hospitalDto.getLongitude());
    }

    private List<HospitalSearchResponseDto> getAllHospitalsFromRedis() {
        List<HospitalSearchResponseDto> hospitalList = new ArrayList<>();
        try {
            for (String hospitalValue : hashOperations.entries(HOSPITAL_HASH_KEY).values()) {
                hospitalList.add(objectMapper.readValue(hospitalValue, HospitalSearchResponseDto.class));
            }
        } catch (JsonProcessingException e) {
            return List.of();
        }
        return hospitalList;
    }

    private int getCurrentAppointmentCount(Long hospitalSeq, LocalDateTime now, LocalDate currentDate) {
        String[] searchString = new String[2];
        int[] dayAddNumber = {0, 0};

        int hour = now.getHour();
        int minute = now.getMinute();

        int result = 0;

        searchString[0] = hour + "30";
        searchString[1] = (hour + 1) + "00";

        if (minute >= 30) {
            searchString[0] = (hour + 1) + "00";
            searchString[1] = (hour + 1) + "30";
        }

        searchString[0] = searchString[0].length() == 3 ? "0" + searchString[0] : searchString[0];
        searchString[1] = searchString[1].length() == 3 ? "0" + searchString[1] : searchString[1];

        if (hour == 23 && minute >= 30) {
            searchString[0] = "0000";
            searchString[1] = "0030";
            dayAddNumber[0] = 1;
            dayAddNumber[1] = 1;
        } else if (hour == 23) {
            searchString[0] = "2330";
            searchString[1] = "0000";
            dayAddNumber[1] = 1;
        }

        result += appointmentRepository
                .getAppointmentCount(hospitalSeq, currentDate.plusDays(dayAddNumber[0]), searchString[0]);
        result += appointmentRepository
                .getAppointmentCount(hospitalSeq, currentDate.plusDays(dayAddNumber[1]), searchString[1]);
        return result;
    }
}
