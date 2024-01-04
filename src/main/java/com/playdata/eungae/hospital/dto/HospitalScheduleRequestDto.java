package com.playdata.eungae.hospital.dto;

import com.playdata.eungae.hospital.domain.HospitalSchedule;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HospitalScheduleRequestDto {

	private String lunchHour;

	private String lunchEndHour;

	private String monOpen;

	private String monClose;

	private String tueOpen;

	private String tueClose;

	private String wedOpen;

	private String wedClose;

	private String thuOpen;

	private String thuClose;

	private String friOpen;

	private String friClose;

	private String satOpen;

	private String satClose;

	private String sunOpen;

	private String sunClose;

	public static HospitalSchedule toEntity(HospitalScheduleRequestDto dto) {
		return HospitalSchedule.builder()
			.lunchHour(dto.getLunchHour())
			.lunchEndHour(dto.getLunchEndHour())
			.monOpen(dto.getMonOpen())
			.monClose(dto.getMonClose())
			.tueOpen(dto.getTueOpen())
			.tueClose(dto.getTueClose())
			.wedOpen(dto.getWedOpen())
			.wedClose(dto.getWedClose())
			.thuOpen(dto.getThuOpen())
			.thuClose(dto.getThuClose())
			.friOpen(dto.getFriOpen())
			.friClose(dto.getFriClose())
			.satOpen(dto.getSatOpen())
			.satClose(dto.getSatClose())
			.sunOpen(dto.getSunOpen())
			.sunClose(dto.getSunClose())
			.build();
	}
}
