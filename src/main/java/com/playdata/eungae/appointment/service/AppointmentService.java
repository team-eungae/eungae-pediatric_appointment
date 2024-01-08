package com.playdata.eungae.appointment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.dto.AppointmentResponseDto;
import com.playdata.eungae.appointment.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;

	// 진료기록 불러오기
	public List<AppointmentResponseDto> getMyMedicalRecords(Long memberSeq) {
		// 현재 status중 "2"가 진료 완료된 상태를 나타내는 값입니다.
		List<Appointment> myMedicalRecords = appointmentRepository.findAllByMemberMemberSeqAndStatus(
			memberSeq, "2").orElseThrow(() -> new IllegalArgumentException("can not find Appointment"));
		// 현재 예약 등록 기능이 없어서 예약등록 기능이 구현된 후에 수정 예정입니다.
		return myMedicalRecords.stream().map(record -> {
			return AppointmentResponseDto.builder()
				.appointmentSeq(record.getMember().getMemberSeq())
				.childrenName(record.getChildren().getName())
				.hospitalName(record.getHospital().getName())
				.doctorName(record.getDoctor().getName())
				.appointmentDate(record.getAppointmentDate())
				.appointmentHour(record.getAppointmentHour())
				.appointmentMinute(record.getAppointmentMinute())
				.build();
		}).collect(Collectors.toList());
	}
}
