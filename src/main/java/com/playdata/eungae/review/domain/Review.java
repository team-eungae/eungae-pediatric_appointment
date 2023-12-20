package com.playdata.eungae.review.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long reviewSeq;
    // @ManyToOne
    // @JoinColumn(name = "hospital_seq")
    // private Hospital hospital;
    // @ManyToOne
    // @JoinColumn(name = "member_seq")
    // private Member member;
    @Column(nullable = false)
    private int starRating;
    @Column(nullable = false)
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
