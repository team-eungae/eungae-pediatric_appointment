package com.playdata.eungae.article.domain;

import com.playdata.eungae.article.dto.CommunityBoardDto;
import com.playdata.eungae.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "community_board")
public class CommunityBoard {
	@Id
	@GeneratedValue
	private Long communityBoardSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private Member member;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	public static CommunityBoard from(CommunityBoardDto dto) {
		CommunityBoard communityBoard = CommunityBoard.builder()
			.title(dto.getTitle())
			.content(dto.getContent())
			.build();
		return communityBoard;
	}

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}