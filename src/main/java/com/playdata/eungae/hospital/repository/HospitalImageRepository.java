package com.playdata.eungae.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.hospital.domain.HospitalImage;

@Repository
public interface HospitalImageRepository extends JpaRepository<HospitalImage, Long> {
	List<HospitalImage> findAllByHospitalHospitalSeq(Long hospitalSeq);


}
