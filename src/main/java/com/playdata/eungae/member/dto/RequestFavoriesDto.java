package com.playdata.eungae.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFavoriesDto {

	@NotNull
	private Long memberSeq;
	@NotNull
	private Long hospitalSeq;

}
