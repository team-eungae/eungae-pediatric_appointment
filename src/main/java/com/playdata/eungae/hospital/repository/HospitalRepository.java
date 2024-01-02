package com.playdata.eungae.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playdata.eungae.hospital.domain.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
