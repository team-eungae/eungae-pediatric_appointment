package com.playdata.eungae.doctor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.doctor.domain.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	public List<Doctor> findAllByHospitalHospitalSeq(Long hospitalSeq);
}
