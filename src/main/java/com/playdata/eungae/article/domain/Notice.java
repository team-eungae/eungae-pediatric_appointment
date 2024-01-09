package com.playdata.eungae.article.domain;

import com.playdata.eungae.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "notice")
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long noticeSeq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

}
