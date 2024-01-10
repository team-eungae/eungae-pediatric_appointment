package com.playdata.eungae.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentStatus;
import com.playdata.eungae.appointment.dto.ResponseMedicalHistoryDto;
import com.playdata.eungae.appointment.dto.ResponseAppointmentDto;
import com.playdata.eungae.appointment.dto.ResponseDetailMedicalHistoryDto;
import com.playdata.eungae.appointment.repository.AppointmentRepository;
import com.playdata.eungae.doctor.repository.DoctorRepository;
import com.playdata.eungae.hospital.domain.HospitalSchedule;
import com.playdata.eungae.hospital.repository.HospitalScheduleRepository;
import com.playdata.eungae.member.domain.Children;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.repository.ChildrenRepository;
import com.playdata.eungae.member.repository.MemberRepository;
import com.playdata.eungae.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppointmentService {

	private static final int PAGE_SIZE = 20;
	private final HospitalScheduleRepository hospitalScheduleRepository;
	private final DoctorRepository doctorRepository;
	private final AppointmentRepository appointmentRepository;
	private final MemberRepository memberRepository;
	private final ChildrenRepository childrenRepository;
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public Optional<List<Children>> getMyChildren(String email) {
		Member member = memberRepository.findByEmail(email).get();
		return childrenRepository.findAllByMemberMemberSeq(member.getMemberSeq());
	}

	// 진료기록 불러오기
	@Transactional(readOnly = true)
	public List<ResponseMedicalHistoryDto> getMyMedicalRecords(String memberEmail) {
		List<Appointment> myMedicalRecords = appointmentRepository.findAllByMemberEmail(memberEmail, AppointmentStatus.DIAGNOSIS);
		if (myMedicalRecords.isEmpty()) {
			throw new IllegalStateException("can not find Appointment");
		}

		return myMedicalRecords.stream()
			.map(ResponseMedicalHistoryDto::toDto)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ResponseDetailMedicalHistoryDto findMedicalHistory(Long appointmentSeq) {

		Appointment appointment = appointmentRepository.findWithReview(appointmentSeq)
			.orElseThrow(() -> new IllegalStateException("Can not found Appointment Entity"));

		ResponseDetailMedicalHistoryDto responseDetailMediclaHistoryDto = ResponseDetailMedicalHistoryDto.toDto(
			appointment);

		responseDetailMediclaHistoryDto.setWriteReview(reviewRepository.findById(appointment.getReviewSeq())
			.isPresent());

		return responseDetailMediclaHistoryDto;

	}

	@Transactional(readOnly = true)
	public Page<ResponseAppointmentDto> findAppointment(int pageNumber, Long memberSeq) {

		Pageable pageConfig = PageRequest.of(
			pageNumber, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt")
		);

		return appointmentRepository.findAllAppointment(pageConfig, memberSeq)
			.orElseThrow(() -> new IllegalStateException("Can not found Appointment Entity"))
			.map(ResponseAppointmentDto::toDto);
	}

/*
	@Transactional(readOnly = true)
	public List<String> getAppointmentPossibleLocalTime(
		String appointmentDate,
		int appointmentDayOfWeek,
		Long doctorSeq,
		Long hospitalSeq) {

	}
*/

	// 병원 운영 시간
	private Map<String, String> getHospitalDutyTime(
		Long hospitalSeq, // 병원 식별자
		int appointmentDayOfWeek /* 요일 */) {

		Map<String, String> hospitalDutyTime = new HashMap<>();

		HospitalSchedule hospitalSchedule = hospitalScheduleRepository.findByHospitalHospitalSeq(hospitalSeq)
			.get();

		String openHour = "";
		String closeHour = "";

		// 점심 시간
		hospitalDutyTime.put("lunchStartHour", hospitalSchedule.getLunchHour());
		hospitalDutyTime.put("lunchEndHour", hospitalSchedule.getLunchEndHour());

		switch (appointmentDayOfWeek) {
			case 1 -> {
				openHour = hospitalSchedule.getMonOpen();
				closeHour = hospitalSchedule.getMonClose();
			}
			case 2 -> {
				openHour = hospitalSchedule.getThuOpen();
				closeHour = hospitalSchedule.getTueClose();
			}
			case 3 -> {
				openHour = hospitalSchedule.getWedOpen();
				closeHour = hospitalSchedule.getWedClose();
			}
			case 4 -> {
				openHour = hospitalSchedule.getThuOpen();
				closeHour = hospitalSchedule.getThuClose();
			}
			case 5 -> {
				openHour = hospitalSchedule.getFriOpen();
				closeHour = hospitalSchedule.getFriClose();
			}
			case 6 -> {
				openHour = hospitalSchedule.getSatOpen();
				closeHour = hospitalSchedule.getSatClose();
			}
			case 0 -> {
				openHour = hospitalSchedule.getSunOpen();
				closeHour = hospitalSchedule.getSunClose();
			}
		}

		hospitalDutyTime.put("openHour", openHour);
		hospitalDutyTime.put("closeHour", closeHour);

		return hospitalDutyTime;
	}

	private LocalTime convertStringToLocalTime(String HHMM) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
		LocalTime localTime = LocalTime.parse(HHMM, formatter);

		return localTime;
	}

	private List<Appointment> getHospitalDutyHour(String appointmentDate, int appointmentDayOfWeek,
		HospitalSchedule hospitalSchedule, Long hospitalSeq, Long doctorSeq) {

		List<String> appointmentTime = new ArrayList<>();

		int doctorTreatmentPossible = doctorRepository.findById(doctorSeq)
			.get()
			.getTreatmentPossible();


		LocalDate parseStringToLocalDate = LocalDate.parse(appointmentDate);

		Map<String, String> hospitalDutyTime = getHospitalDutyTime(hospitalSeq, appointmentDayOfWeek);
		String openHour = hospitalDutyTime.get("openHour");
		convertStringToLocalTime(openHour);

		List<Appointment> allWithHospital = new ArrayList<>();

		// for (; !localTime.isAfter(closeTime); localTime = localTime.plusMinutes(30)) {
		// 	log.info(localTime + "");
		// 	String formatTime = localTime.format(formatter);
		// 	log.warn("{}", hospitalSeq);
		// 	log.warn("{}", parseStringToLocalDate);
		//
		// 	allWithHospital.addAll(appointmentRepository.findAllWithHospital(
		// 		hospitalSeq,
		// 		parseStringToLocalDate,
		// 		formatTime
		// 	));
		// }
		return allWithHospital;
	}

	public List<Appointment> getHospitalSchedule(String appointmentDate, int appointmentDayOfWeek,
		Long doctorSeq, Long hospitalSeq) {

		HospitalSchedule hospitalSchedule = hospitalScheduleRepository.findByHospitalHospitalSeq(hospitalSeq)
			.get();
		List<Appointment> hospitalDutyHour = getHospitalDutyHour(appointmentDate, appointmentDayOfWeek,
			hospitalSchedule, hospitalSeq, doctorSeq);
		for (Appointment appointment : hospitalDutyHour) {
			log.info("============================={}============================", appointment.getAppointmentSeq());
		}
		return hospitalDutyHour;
	}

	public ResponseDetailMedicalHistoryDto getMyMedicalRecordDetail(Long appointmentSeq) {
		Appointment appointment = appointmentRepository.findByAppointmentSeq(appointmentSeq, AppointmentStatus.DIAGNOSIS)
			.orElseThrow(() -> new IllegalStateException("Can not found Appointment. appointmentSeq = {%d}".formatted(appointmentSeq)));
		return ResponseDetailMedicalHistoryDto.toDto(appointment);
	}
}
