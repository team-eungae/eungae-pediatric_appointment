package com.playdata.eungae.hospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.hospital.domain.HospitalSchedule;

@Repository
public interface HospitalScheduleRepository extends JpaRepository<HospitalSchedule, Long> {

	@Query("select h from HospitalSchedule h"
		+ " where h.hospital.hospitalSeq = :hospitalSeq"
		+ " and h.deleteYN = 'N'")
	Optional<HospitalSchedule> findByHospitalHospitalSeq(@Param("hospitalSeq") Long hospitalSeq);

}
