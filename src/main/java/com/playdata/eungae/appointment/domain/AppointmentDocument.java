package com.playdata.eungae.appointment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment_document")
public class AppointmentDocument {
    @Id
    @GeneratedValue
    private Long appointmentDocumentSeq;
//    @ManyToOne
//    @JoinColumn(name = "appointment_seq")
//    private Appointment appointment;
    private String appointmentDocumentLoc;
    private String note;    //의사소견 텍스트



//    appointment_document_seq number [pk, increment]
//    appointment_seq number
//    appointment_document_loc varchar2 [null]
//    note clob [null] // 의사소견 텍스트
//    created_at timestamp [default:"sysdate"]
//    modified_at timestamp [default:"sysdate"]
}
