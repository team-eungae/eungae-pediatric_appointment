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
import com.playdata.eungae.appointment.dto.AppointmentRequestDto;
import com.playdata.eungae.appointment.dto.AppointmentResponseDto;
import com.playdata.eungae.appointment.dto.ResponseAppointmentDto;
import com.playdata.eungae.appointment.dto.ResponseDetailMedicalHistoryDto;
import com.playdata.eungae.appointment.repository.AppointmentRepository;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.doctor.dto.DoctorViewResponseDto;
import com.playdata.eungae.doctor.repository.DoctorRepository;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.domain.HospitalSchedule;
import com.playdata.eungae.hospital.repository.HospitalRepository;
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

	private final HospitalRepository hospitalRepository;
	private final HospitalScheduleRepository hospitalScheduleRepository;
	private final DoctorRepository doctorRepository;
	private final AppointmentRepository appointmentRepository;
	private final MemberRepository memberRepository;
	private final ChildrenRepository childrenRepository;
	private final ReviewRepository reviewRepository;

	private static final int PAGE_SIZE = 20;

	@Transactional(readOnly = true)
	public Optional<List<Children>> getMyChildren(String email) {
		Member member = memberRepository.findByEmail(email).get();
		return childrenRepository.findAllByMemberMemberSeq(member.getMemberSeq());
	}

	@Transactional(readOnly = true)
	public List<DoctorViewResponseDto> getDoctors(Long hospitalSeq) {
		return doctorRepository.findAllByHospitalHospitalSeq(hospitalSeq)
			.get()
			.stream()
			.map(DoctorViewResponseDto::toDto)
			.collect(Collectors.toList());
	}

	@Transactional
	public void saveAppointment(AppointmentRequestDto requestDto, String email) {

		Hospital hospital = hospitalRepository.findById(requestDto.getHospitalSeq()).get();
		Doctor doctor = doctorRepository.findById(requestDto.getDoctorSeq()).get();
		Children children = childrenRepository.findById(requestDto.getChildrenSeq()).get();
		Member member = memberRepository.findByEmail(email).get();

		Appointment appointment = AppointmentRequestDto.toEntity(requestDto, hospital, children, doctor, member);

		appointmentRepository.save(appointment);
	}

	@Transactional(readOnly = true)
	public List<AppointmentResponseDto> getMyMedicalRecords(Long memberSeq) {
		// 현재 status중 "2"가 진료 완료된 상태를 나타내는 값입니다.
		List<Appointment> myMedicalRecords = appointmentRepository.findAllByMemberMemberSeqAndStatus(
			memberSeq, "2").orElseThrow(() -> new IllegalArgumentException("can not find Appointment"));
		// 현재 예약 등록 기능이 없어서 예약등록 기능이 구현된 후에 수정 예정입니다.
		return myMedicalRecords.stream().map(appointment -> {
			return AppointmentResponseDto.builder()
				.appointmentSeq(appointment.getMember().getMemberSeq())
				.childrenName(appointment.getChildren().getName())
				.hospitalName(appointment.getHospital().getName())
				.doctorName(appointment.getDoctor().getName())
				.appointmentDate(appointment.getAppointmentDate())
				.appointmentHHMM(appointment.getAppointmentHHMM())
				.build();
		}).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ResponseDetailMedicalHistoryDto findMedicalHistory(Long appointmentSeq) {

		Appointment appointment = appointmentRepository.findAllWithReview(appointmentSeq)
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

		return appointmentRepository.findAppointment(pageConfig, memberSeq)
			.orElseThrow(() -> new IllegalStateException("Can not found Appointment Entity"))
			.map(ResponseAppointmentDto::toDto);
	}

	@Transactional(readOnly = true)
	public List<LocalTime> createAppointmentPossibleTime
		(String appointmentDate,
			int appointmentDayOfWeek,
			Long doctorSeq,
			Long hospitalSeq) {

		Map<String, String> hospitalDutyTime = getHospitalDutyTime(hospitalSeq, appointmentDayOfWeek);
		String openHour = hospitalDutyTime.get("openHour");
		String closeHour = hospitalDutyTime.get("closeHour");
		String lunchStartHour = hospitalDutyTime.get("lunchStartHour");
		String lunchEndHour = hospitalDutyTime.get("lunchEndHour");

		LocalTime convertOpenHour = convertStringToLocalTime(openHour);
		LocalTime convertCloseHour = convertStringToLocalTime(closeHour);
		LocalTime convertLunchStartHour = convertStringToLocalTime(lunchStartHour);
		LocalTime convertLunchEndHour = convertStringToLocalTime(lunchEndHour);

		List<LocalTime> hospitalDutyTimeList = new ArrayList<>();

		LocalDate localDate = convertStringToLocalDate(appointmentDate);

		for (; !convertOpenHour.isAfter(convertCloseHour); convertOpenHour = convertOpenHour.plusMinutes(30)) {

			if (!convertOpenHour.isBefore(convertLunchStartHour)
				&& convertOpenHour.isBefore(convertLunchEndHour)) {
				continue;
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
			String convertLocalTimeToString = convertOpenHour.format(formatter);

			int currentAppointmentCount = appointmentRepository.findAllWithHospital(hospitalSeq, localDate,
				convertLocalTimeToString, doctorSeq).size();

			Integer doctorTreatmentPossibleCount = getDoctorTreatmentPossibleCount(doctorSeq);

			if (doctorTreatmentPossibleCount > currentAppointmentCount) {
				hospitalDutyTimeList.add(convertOpenHour);
			}
		}
		return hospitalDutyTimeList;
	}

	// 병원 운영 시간
	private Map<String, String> getHospitalDutyTime(
		Long hospitalSeq, // 병원 식별자
		int appointmentDayOfWeek /* 요일 */) {

		Map<String, String> hospitalDutyTime = new HashMap<>();

		log.info("hospitalSchedule" + hospitalSeq);

		// 병원의 월~일 운영시간 및 점심시간 전체
		HospitalSchedule hospitalSchedule = hospitalScheduleRepository.findByHospitalHospitalSeq(hospitalSeq)
			.orElseThrow(() -> new NullPointerException("Null Hospital Schedule"));

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

	// DB에 있는 운영시간을 String -> LocalTime 변환
	private LocalTime convertStringToLocalTime(String HHMM) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

		return LocalTime.parse(HHMM, formatter);
	}

	// DB에 있는 운영시간을 String -> LocalTime 변환
	private LocalDate convertStringToLocalDate(String date) {
		return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
	}

	private Integer getDoctorTreatmentPossibleCount(Long doctorSeq) {
		return doctorRepository.findById(doctorSeq).get().getTreatmentPossible();
	}
}
