package com.playdata.eungae.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long memberSeq;

    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    private String birthDate;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String zipCode;

    private Integer xCoordinate;

    private Integer yCoordinate;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
