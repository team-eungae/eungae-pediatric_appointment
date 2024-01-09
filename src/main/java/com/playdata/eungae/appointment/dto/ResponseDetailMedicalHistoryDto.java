package com.playdata.eungae.appointment.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.appointment.domain.AppointmentDocument;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Children;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDetailMedicalHistoryDto {

	private Long appointmentSeq;

	private String childrenName;

	private String doctorName;

	private String hospitalName;

	// 리뷰 작성 유무
	// 어떻게 처리할지
	private boolean writeReview;

	private LocalDate appointmentDate;

	private String appointmentHHMM;

	public static ResponseDetailMedicalHistoryDto toDto(Appointment appointment) {
		return ResponseDetailMedicalHistoryDto.builder()
			.appointmentSeq(appointment.getAppointmentSeq())
			.childrenName(appointment.getChildren().getName())
			.doctorName(appointment.getDoctor().getName())
			.hospitalName(appointment.getHospital().getName())
			.appointmentDate(appointment.getAppointmentDate())
			.appointmentHHMM(appointment.getAppointmentHHMM())
			.build();
	}

}
