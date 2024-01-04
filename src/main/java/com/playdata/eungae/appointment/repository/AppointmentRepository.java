package com.playdata.eungae.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playdata.eungae.appointment.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
