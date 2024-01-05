package com.playdata.eungae.appointment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.dto.ResponseAppointmentDto;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	@Query("select a from Appointment a"
			+ " join fetch a.hospital"
			+ " join fetch a.member"
			+ " where a.appointmentSeq = :appointmentSeq")
	Optional<Appointment> findByIdWhitHospital(@Param("appointmentSeq") long appointmentSeq);

	// 이게 될까?
	@Query("select new com.playdata.eungae.appointment.dto.ResponseAppointmentDto(a.appointmentSeq, r.reviewSeq)"
		+ " from Appointment a, Review r"
		+ " where a.review_seq = r.reviewSeq"
		+ " and a.member.memberSeq = :memberSeq")
	Optional<Page<ResponseAppointmentDto>> findAllWithReview(Pageable pageConfig, @Param("memberSeq") Long memberSeq);
}
