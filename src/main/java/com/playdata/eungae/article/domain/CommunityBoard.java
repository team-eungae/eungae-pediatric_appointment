package com.playdata.eungae.article.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "community_board")
public class CommunityBoard {
    @Id
    @GeneratedValue
    private Long communityBoard;
//    @ManyToOne
//    @JoinColumn(name = "member_seq")
//    private Member member;
    @Column(nullable = false)
    private String title;   //제목
    @Column(nullable = false)
    private String content; //내용

//    community_board number [pk, increment]
//    member_seq number [not null]
//    title varchar2 [not null]
//    content clob [not null]
//    created_at timestamp [default:"sysdate"]
//    modified_at
}
