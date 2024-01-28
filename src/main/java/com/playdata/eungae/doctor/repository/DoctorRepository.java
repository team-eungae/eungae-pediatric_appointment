package com.playdata.eungae.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.doctor.domain.DoctorStatus;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	@Query("select d from Doctor d"
		+ " where d.hospital.hospitalSeq = :hospitalSeq"
		+ " and d.status = :doctorStatus"
		+ " and d.deleteYN = 'N'")
	List<Doctor> findAllByHospitalHospitalSeq(@Param("hospitalSeq") Long hospitalSeq, @Param("doctorStatus") DoctorStatus doctorStatus);
}
