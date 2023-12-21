package com.playdata.eungae.article.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

import com.playdata.eungae.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "community_board")
public class CommunityBoard {
	@Id
	@GeneratedValue
	private Long communityBoard;

	@ManyToOne
	@JoinColumn(name = "member_seq")
	private Member member;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;
}
