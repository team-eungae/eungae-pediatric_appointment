package com.playdata.eungae.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestFavoriesDto {

	@NotNull
	private Long memberSeq;
	@NotNull
	private Long HospitalSeq;

}
