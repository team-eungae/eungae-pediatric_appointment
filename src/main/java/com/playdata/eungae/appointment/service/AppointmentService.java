package com.playdata.eungae.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.playdata.eungae.appointment.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentStatus;
import com.playdata.eungae.appointment.repository.AppointmentRepository;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.doctor.dto.DoctorResponseDto;
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

	private static final int PAGE_SIZE = 20;
	private final HospitalRepository hospitalRepository;
	private final HospitalScheduleRepository hospitalScheduleRepository;
	private final DoctorRepository doctorRepository;
	private final AppointmentRepository appointmentRepository;
	private final MemberRepository memberRepository;
	private final ChildrenRepository childrenRepository;
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public List<Children> getMyChildren(String email) {
		Member member = memberRepository.findByEmail(email).get();
		return childrenRepository.findAllByMemberMemberSeq(member.getMemberSeq());
	}

	@Transactional(readOnly = true)
	public List<DoctorResponseDto> getDoctors(Long hospitalSeq) {
		return doctorRepository.findAllByHospitalHospitalSeq(hospitalSeq)
			.stream()
			.map(DoctorResponseDto::toDto)
			.collect(Collectors.toList());
	}

	@Transactional
	public ResponseAppointmentDto deleteAppointment(Long appointmentSeq) {
		Appointment appointment = appointmentRepository.findById(appointmentSeq)
			.orElseThrow(() -> new IllegalStateException(
				"Can not found appointment, appointmentSeq = {%d}".formatted(appointmentSeq)));
		appointment.setStatus(AppointmentStatus.CANCEL);
		return ResponseAppointmentDto.toDto(appointment);

	}

	@Transactional
	public void saveAppointment(AppointmentRequestDto requestDto, String email) {

		Hospital hospital = hospitalRepository.findById(requestDto.getHospitalSeq())
			.orElseThrow(() -> new IllegalStateException(
				"Can not found Hospital. hospitalSeq = {%d}".formatted(requestDto.getHospitalSeq())));
		Doctor doctor = doctorRepository.findById(requestDto.getDoctorSeq())
			.orElseThrow(() -> new IllegalStateException(
				"Can not found Doctor. doctorSeq = {%d}".formatted(requestDto.getDoctorSeq())));
		Children children = childrenRepository.findById(requestDto.getChildrenSeq())
			.orElseThrow(() -> new IllegalStateException(
				"Can not found ChildrenSeq. childrenSeq = {%d}".formatted(requestDto.getChildrenSeq())));
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalStateException("Can not found Member. memberEmail = {%s}".formatted(email)));

		Appointment appointment = AppointmentRequestDto.toEntity(requestDto, hospital, children, doctor, member);

		appointmentRepository.save(appointment);
	}

	@Transactional(readOnly = true)
	public List<ResponseMedicalHistoryDto> getMyMedicalRecords(String memberEmail) {
		List<Appointment> myMedicalRecords = appointmentRepository.findAllByMemberEmail(memberEmail);

		// 진료 기록만 조회
		return myMedicalRecords.stream()
			.filter((appointment -> appointment.getStatus() == AppointmentStatus.DIAGNOSIS))
			.map(ResponseMedicalHistoryDto::toDto)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ResponseDetailMedicalHistoryDto getMyMedicalRecordDetail(Long appointmentSeq) {
		Appointment appointment = appointmentRepository.findByAppointmentSeq(appointmentSeq,
				AppointmentStatus.DIAGNOSIS)
			.orElseThrow(() -> new IllegalStateException(
				"Can not found Appointment. appointmentSeq = {%d}".formatted(appointmentSeq)));
		return ResponseDetailMedicalHistoryDto.toDto(appointment);
	}

	@Transactional(readOnly = true)
	public List<ResponseAppointmentDto> getAppointmentListByMemberEmail(/*int pageNumber,*/ String memberEmail) {

/*
		고도화 작업하며 pageable 기능 추가할것
		Pageable pageConfig = PageRequest.of(
			pageNumber, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt")
		);
*/
    
		List<Appointment> appointments = appointmentRepository.findAllByMemberEmail(memberEmail);

		// 유효한 예약과 취소된 예약을 조회
		return appointments.stream()
			.map(ResponseAppointmentDto::toDto)
			.filter((responseAppointmentDto) -> responseAppointmentDto.getStatus() != AppointmentStatus.DIAGNOSIS)
			.collect(Collectors.toList());
	}

	// 병원 운영 시간

	@Transactional(readOnly = true)
	public List<LocalTime> createAppointmentPossibleTime
		(String appointmentDate,
			int appointmentDayOfWeek,
			Long doctorSeq,
			Long hospitalSeq,
			Long childrenSeq
		) {
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

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

		while (!convertOpenHour.isAfter(convertCloseHour)) {
			if (!isDuringLunch(convertOpenHour, convertLunchStartHour, convertLunchEndHour)) {
				String convertLocalTimeToString = convertOpenHour.format(formatter);
				int currentAppointmentCount = getAppointmentCount(hospitalSeq, localDate, convertLocalTimeToString,
					doctorSeq);
				int doctorTreatmentPossibleCount = getDoctorTreatmentPossibleCount(doctorSeq);

				if (doctorTreatmentPossibleCount > currentAppointmentCount) {
					hospitalDutyTimeList.add(convertOpenHour);
				}
			}
			convertOpenHour = convertOpenHour.plusMinutes(30);
		}

		return hospitalDutyTimeList;
	}

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

	@Transactional(readOnly = true)
	public boolean getChildrenAppointmentTime(Long childrenSeq, String appointmentDate) {

		Map<String, String> childrenAppointments = new HashMap<>();

		Children children = childrenRepository.findById(childrenSeq)
			.orElseThrow(() -> new NoSuchElementException("Can not found children"));

		List<Appointment> appointmentList = children.getAppointmentList();

		if (appointmentList.isEmpty()) {
			return false;
		}
		return children.getAppointmentList()
			.stream()
			.anyMatch((appointment -> appointment.getStatus() == AppointmentStatus.APPOINTMENT
				&& appointment.getAppointmentDate()
				.isEqual(convertStringToLocalDate(appointmentDate))));
	}

	// DB에 있는 운영시간을 String -> LocalTime 변환
	private LocalTime convertStringToLocalTime(String hourMinute) {
		// hourMinute는 HHMM의 형식입니다. 10시 30분이라면 1030

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

		return LocalTime.parse(hourMinute, formatter);
	}

	// DB에 있는 운영시간을 String -> LocalTime 변환
	private LocalDate convertStringToLocalDate(String date) {
		return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
	}

	private Integer getDoctorTreatmentPossibleCount(Long doctorSeq) {
		return doctorRepository.findById(doctorSeq)
			.get()
			.getTreatmentPossible();
	}

	public void checkAppointmentStatus(Long appointmentSeq) {
		Appointment appointment = appointmentRepository.findById(appointmentSeq)
			.orElseThrow(() -> new IllegalStateException(
				"Can not found Appointment. appointmentSeq = {%d}".formatted(appointmentSeq)));

		if (appointment.getStatus() != AppointmentStatus.DIAGNOSIS) {
			throw new IllegalStateException("appointment is not diagnosed");
		}
	}

	private boolean isDuringLunch(LocalTime time, LocalTime lunchStart, LocalTime lunchEnd) {
		return !time.isBefore(lunchStart) && time.isBefore(lunchEnd);
	}

	private int getAppointmentCount(Long hospitalSeq, LocalDate localDate, String time, Long doctorSeq) {
		return appointmentRepository.findAllWithHospital(hospitalSeq, localDate, time, doctorSeq).size();
	}    

    @Transactional
    public VisitedChangeStatusDto changeAppointmentStatus(Long appointmentSeq) {

        Appointment appointment = appointmentRepository.findById(appointmentSeq)
                .orElseThrow(() -> new IllegalStateException(
                        "Cannot find Appointment. appointmentSeq = {%d}".formatted(appointmentSeq)));

         appointment.setStatus(AppointmentStatus.DIAGNOSIS);

         return VisitedChangeStatusDto.toDto(appointment);
    }
}
