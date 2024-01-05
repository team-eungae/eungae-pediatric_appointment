package com.playdata.eungae.appointment.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.dto.ResponseDetailMedicalHistoryDto;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	@Query("select a from Appointment a"
			+ " join fetch a.hospital"
			+ " join fetch a.member"
			+ " where a.appointmentSeq = :appointmentSeq")
	Optional<Appointment> findByIdWhitHospital(@Param("appointmentSeq") long appointmentSeq);

	@Query("select a"
		+ " from Appointment a"
		+ " join fetch a.member m"
		+ " join fetch a.doctor d"
		+ " join fetch a.hospital h"
		+ " where a.appointmentSeq = :appointmentSeq")
	Optional<Appointment> findAllWithReview(@Param("appointmentSeq") Long appointmentSeq);
}
