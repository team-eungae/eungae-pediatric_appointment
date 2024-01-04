package com.playdata.eungae.appointment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.member.domain.Member;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	// 내 진료기록 전체 조회
	Optional<List<Appointment>> findAllByMemberMemberSeqAndStatus(Long memberSeq, String status);
}
