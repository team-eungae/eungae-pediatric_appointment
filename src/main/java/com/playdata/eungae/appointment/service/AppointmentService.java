package com.playdata.eungae.appointment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.dto.AppointmentResponseDto;
import com.playdata.eungae.appointment.repository.AppointmentRepository;
import com.playdata.eungae.member.domain.Children;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.repository.ChildrenRepository;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playdata.eungae.appointment.dto.ResponseAppointmentDto;
import com.playdata.eungae.appointment.dto.ResponseDetailMedicalHistoryDto;
import com.playdata.eungae.review.repository.ReviewRepository;    
  
@Slf4j
@RequiredArgsConstructor
@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final MemberRepository memberRepository;
	private final ChildrenRepository childrenRepository;
  private final ReviewRepository reviewRepository;
	private static final int PAGE_SIZE = 20;


	public Optional<List<Children>> getMyChildren(String email) {
		Member member = memberRepository.findByEmail(email).get();
		return childrenRepository.findAllByMemberMemberSeq(member.getMemberSeq());
	}

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

	@Transactional(readOnly = true)
	public ResponseDetailMedicalHistoryDto findMedicalHistory(Long appointmentSeq) {

		Appointment appointment = appointmentRepository.findAllWithReview(appointmentSeq)
			.orElseThrow(() -> new IllegalStateException("Can not found Appointment Entity"));

		ResponseDetailMedicalHistoryDto responseDetailMediclaHistoryDto = ResponseDetailMedicalHistoryDto.toDto(appointment);

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
