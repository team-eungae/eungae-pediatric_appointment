package com.playdata.eungae.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.hospital.domain.HospitalImage;

@Repository
public interface HospitalImageRepository extends JpaRepository<HospitalImage, Long> {

	@Query("select h from HospitalImage h"
		+ " where h.hospital.hospitalSeq = :hospitalSeq"
		+ " and h.deleteYN = 'N'")
	List<HospitalImage> findAllByHospitalHospitalSeq(@Param("hospitalSeq") Long hospitalSeq);

}
