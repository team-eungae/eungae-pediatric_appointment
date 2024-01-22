package com.playdata.eungae.hospital.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.hospital.domain.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	@Query("select h"
		+ " from Hospital h"
		+ " where h.name like %:keyword% or h.address like %:keyword%")
	List<Hospital> findAllByKeyword(@Param("keyword") String keyword);
	
	Optional<Hospital> findByHospitalId(String hospitalId);
}
