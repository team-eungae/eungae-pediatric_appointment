package com.playdata.eungae.hospital.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hospital_schedule")
public class HospitalSchedule {
    @Id
    @GeneratedValue
    private Long hospitalScheduleSeq; // 병원스케쥴 식별자
//    @ManyToOne
//    @JoinColumn(name = "hospital_seq")
//    private Hospital hospital;  // 병원식별자
    @Column(nullable = false)
    private Date days;  // 0: 월요일 ~ 6: 일요일
    @Column(nullable = false)
    private Timestamp openingTime;    // HHMM
    @Column(nullable = false)
    private Timestamp closingTime;    // HHMM

//    hospital_schedule_seq number [pk, increment]
//    hospital_seq number // 병원 식별자
//    days varchar2 [not null] // 0: 월요일 ~ 6: 일요일
//    opening_time varchar2 [not null] // HHMM
//    closing_time varchar2 [not null] // HHMM
}
