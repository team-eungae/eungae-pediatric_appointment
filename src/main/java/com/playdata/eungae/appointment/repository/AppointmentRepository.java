package com.playdata.eungae.appointment.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.playdata.eungae.appointment.domain.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.appointment.domain.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a"
            + " join fetch a.hospital"
            + " join fetch a.member"
            + " where a.appointmentSeq = :appointmentSeq"
            + " and a.deleteYN = 'N'")
    Optional<Appointment> findByIdWithHospitalWithMember(@Param("appointmentSeq") long appointmentSeq);

    @Query("select a"
            + " from Appointment a"
            + " join fetch a.hospital h"
            + " where a.hospital.hospitalSeq = :hospitalSeq"
            + " and a.appointmentDate = :appointmentDate"
            + " and a.appointmentHHMM = :appointmentHHMM"
            + " and a.status = 'APPOINTMENT'"
            + " and a. doctor.doctorSeq = :doctorSeq"
            + " and a.deleteYN = 'N'")
    List<Appointment> findAllWithHospital(
            @Param("hospitalSeq") Long hospitalSeq,
            @Param("appointmentDate") LocalDate appointmentDate,
            @Param("appointmentHHMM") String formatTime,
            @Param("doctorSeq") Long doctorSeq
    );

    @Query("select count(*) from Appointment a"
            + " join a.hospital h"
            + " where a.hospital.hospitalSeq = :hospitalSeq"
            + " and a.appointmentDate = :appointmentDate"
            + " and a.appointmentHHMM = :appointmentHHMM"
            + " and a.status = 'APPOINTMENT'"
            + " and a.deleteYN = 'N'")
    Long getAppointmentCount(
            @Param("hospitalSeq") Long hospitalSeq,
            @Param("appointmentDate") LocalDate appointmentDate,
            @Param("appointmentHHMM") String appointmentHHMM);

    @Query("select a"
            + " from Appointment a"
            + " join fetch a.member"
            + " join fetch a.hospital "
            + " join fetch a.doctor "
            + " join fetch a.children "
            + " where a.member.email = :memberEmail"
            + " and a.deleteYN = 'N'"
            + " order by a.appointmentSeq desc ")
    List<Appointment> findAllByMemberEmail(@Param("memberEmail") String memberEmail);

    @Query("select a"
            + " from Appointment a"
            + " join fetch a.hospital "
            + " join fetch a.doctor "
            + " join fetch a.children "
            + " join fetch a.member"
            + " where a.appointmentSeq = :appointmentSeq"
            + " and a.deleteYN = 'N'"
            + " and a.status = :appointmentStatus")
    Optional<Appointment> findByAppointmentSeq(
            @Param("appointmentSeq") Long appointmentSeq,
            @Param("appointmentStatus") AppointmentStatus appointmentStatus);


}