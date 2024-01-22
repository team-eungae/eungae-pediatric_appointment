package com.playdata.eungae.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class KeywordSearchRequestDto {
	@NotBlank(message = "Keyword can not contain blank")
	@Size(min = 2, message = "Keyword can not be less than two letters")
	private String keyword;
	private Double longitude;
	private Double latitude;

	public boolean hasLocationInfo() {
		return !(getLatitude() == null || getLongitude() == null);
	}
}
