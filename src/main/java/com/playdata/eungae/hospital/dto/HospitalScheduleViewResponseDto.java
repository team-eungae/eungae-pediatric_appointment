package com.playdata.eungae.hospital.dto;

import com.playdata.eungae.hospital.domain.HospitalSchedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalScheduleViewResponseDto {

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

	public static HospitalScheduleViewResponseDto toDto(HospitalSchedule hospitalSchedule){
		return HospitalScheduleViewResponseDto.builder()
			.lunchHour(hospitalSchedule.getLunchHour())
			.lunchEndHour(hospitalSchedule.getLunchEndHour())
			.monOpen(hospitalSchedule.getMonOpen())
			.monClose(hospitalSchedule.getMonClose())
			.tueOpen(hospitalSchedule.getTueOpen())
			.tueClose(hospitalSchedule.getTueClose())
			.wedOpen(hospitalSchedule.getWedOpen())
			.wedClose(hospitalSchedule.getWedClose())
			.thuOpen(hospitalSchedule.getThuOpen())
			.thuClose(hospitalSchedule.getThuClose())
			.friOpen(hospitalSchedule.getFriOpen())
			.friClose(hospitalSchedule.getFriClose())
			.satOpen(hospitalSchedule.getSatOpen())
			.satClose(hospitalSchedule.getSatClose())
			.sunOpen(hospitalSchedule.getSunOpen())
			.sunClose(hospitalSchedule.getSunClose())
			.build();
	}

}
