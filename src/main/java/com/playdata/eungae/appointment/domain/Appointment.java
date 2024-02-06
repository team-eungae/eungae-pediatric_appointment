package com.playdata.eungae.appointment.domain;

import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.doctor.domain.Doctor;
import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.member.domain.Children;
import com.playdata.eungae.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
@Table(name = "appointment")
@Builder
@Entity
public class Appointment extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long appointmentSeq;

    @JoinColumn(name = "member_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "children_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Children children;

    @JoinColumn(name = "doctor_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;

    @JoinColumn(name = "hospital_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;

    @Builder.Default
    @OneToMany(mappedBy = "appointment")
    private List<AppointmentDocument> appointmentDocuments = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private String appointmentHHMM;

    @Setter
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Setter
    @Enumerated(EnumType.STRING)
    private AppointmentSort sort = AppointmentSort.EUNGAE;

    @Column
    private String note;

    // join seq
    @Setter
    private Long reviewSeq;
}
