package com.playdata.eungae.hospital.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hospital {

	@Id @GeneratedValue
	private Long hospitalSeq;

/*
	hospital schedule 과의 연관관계 주인입니다
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(
	private HospitalSchedule hospitalSchedule;
*/

/*
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_seq")
	private List<Doctor> doctor = new ArrayList<>();
*/

	private String password;
	private String name;
	// clob 타입을 String으로 처리할지 고민해봐야 할 것 같습니다
	private String notice;
	// 어떤 컬럼인가요?
	private Long deposit;
	// 연락처?
	private String contact;
	private String address;
	private String addressDetail;
	private Long lunchTime;
	private Long lunchEndTime;
	private String businessRegistration;

	// 메타데이터로 처리하죵
	// 방법은 다같이 알아봐영
	private LocalDateTime createAt;
	private LocalDateTime modifiedAt;


}