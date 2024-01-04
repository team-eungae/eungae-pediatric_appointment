package com.playdata.eungae.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.hospital.domain.HospitalSchedule;

@Repository
public interface HospitalScheduleRepository extends JpaRepository<HospitalSchedule, Long> {
}
