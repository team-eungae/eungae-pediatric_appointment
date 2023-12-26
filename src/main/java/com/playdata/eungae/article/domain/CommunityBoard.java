package com.playdata.eungae.article.domain;

import com.playdata.eungae.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
