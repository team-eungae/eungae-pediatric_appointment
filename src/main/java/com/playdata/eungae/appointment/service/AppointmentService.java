package com.playdata.eungae.appointment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.appointment.dto.ResponseAppointmentDto;
import com.playdata.eungae.appointment.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final int PAGE_SIZE = 20;

	@Transactional(readOnly = true)
	public Page<ResponseAppointmentDto> findAppointments(int page, Long memberSeq) {

		Pageable pageConfig = PageRequest.of(
			page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt")
		);
		return appointmentRepository.findAllWithReview(pageConfig, memberSeq)
			.orElseThrow(() -> new IllegalStateException("Can not found ResponseAppointmentDto"));
	}
}
