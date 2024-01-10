package com.playdata.eungae.appointment.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.appointment.domain.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	// 내 진료기록 전체 조회
	Optional<List<Appointment>> findAllByMemberMemberSeqAndStatus(Long memberSeq, String status);

	@Query("select a from Appointment a"
		+ " join fetch a.hospital"
		+ " where a.hospital.hospitalSeq = :hospitalSeq"
		+ " and a.appointmentDate = :appointmentDate"
		+ " and a.appointmentHHMM = :appointmentHHMM")
	List<Appointment> findAllAppointment(Long hospitalSeq, String appointmentDate, String appointmentHHMM);

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
		+ " join fetch m.children c"
		+ " where a.appointmentSeq = :appointmentSeq")
	Optional<Appointment> findAllWithReview(@Param("appointmentSeq") Long appointmentSeq);

	@Query("select a"
		+ " from Appointment a"
		+ " join fetch a.member m"
		+ " join fetch a.doctor d"
		+ " join fetch a.hospital h"
		+ " join fetch m.children c"
		+ " where m.memberSeq = :memberSeq")
	Optional<Page<Appointment>> findAppointment(Pageable pageConfig, @Param("memberSeq") Long memberSeq);

	@Query("select a"
		+ " from Appointment a"
		+ " join fetch a.hospital h"
		+ " where a.hospital.hospitalSeq = :hospitalSeq"
		+ " and a.appointmentDate = :appointmentDate"
		+ " and a.appointmentHHMM = :appointmentHHMM"
		+ " and a.status = 'APPOINTMENT'"
		+ " and a. doctor.doctorSeq = :doctorSeq")
	List<Appointment> findAllWithHospital(
		@Param("hospitalSeq") Long hospitalSeq,
		@Param("appointmentDate") LocalDate appointmentDate,
		@Param("appointmentHHMM") String formatTime,
		@Param("doctorSeq") Long doctorSeq);
}
