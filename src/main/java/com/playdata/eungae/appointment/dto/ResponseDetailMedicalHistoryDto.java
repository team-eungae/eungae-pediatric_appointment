package com.playdata.eungae.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDetailMedicalHistoryDto {

	// 화면 스펙에 맞게 수정 필요
	private Long appointmentSeq;
	private boolean writeReview;
	private String hospitalName;
	private String memberName;
	private String doctorName;

}
