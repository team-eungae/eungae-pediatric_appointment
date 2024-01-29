package com.playdata.eungae.appointment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResultDto {

	private Long hospitalSeq;

	private boolean paymentResult;

	private String paymentResultInfo;

	public static PaymentResultDto create(Long hospitalSeq, boolean paymentResult, String paymentResultInfo) {
		return PaymentResultDto.builder()
			.hospitalSeq(hospitalSeq)
			.paymentResult(paymentResult)
			.paymentResultInfo(paymentResultInfo)
			.build();
	}

}
