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
			.lunchHour(formatHour(hospitalSchedule.getLunchHour()))
			.lunchEndHour(formatHour(hospitalSchedule.getLunchEndHour()))
			.monOpen(formatHour(hospitalSchedule.getMonOpen()))
			.monClose(formatHour(hospitalSchedule.getMonClose()))
			.tueOpen(formatHour(hospitalSchedule.getTueOpen()))
			.tueClose(formatHour(hospitalSchedule.getTueClose()))
			.wedOpen(formatHour(hospitalSchedule.getWedOpen()))
			.wedClose(formatHour(hospitalSchedule.getWedClose()))
			.thuOpen(formatHour(hospitalSchedule.getThuOpen()))
			.thuClose(formatHour(hospitalSchedule.getThuClose()))
			.friOpen(formatHour(hospitalSchedule.getFriOpen()))
			.friClose(formatHour(hospitalSchedule.getFriClose()))
			.satOpen(formatHour(hospitalSchedule.getSatOpen()))
			.satClose(formatHour(hospitalSchedule.getSatClose()))
			.sunOpen(formatHour(hospitalSchedule.getSunOpen()))
			.sunClose(formatHour(hospitalSchedule.getSunClose()))
			.build();

	}

	private static String formatHour(String data){
		return data.substring(0,2)+":"+data.substring(2);
	}


}
