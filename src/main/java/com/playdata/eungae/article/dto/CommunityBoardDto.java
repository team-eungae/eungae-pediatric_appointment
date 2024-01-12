package com.playdata.eungae.article.dto;

import java.time.format.DateTimeFormatter;

import com.playdata.eungae.article.domain.CommunityBoard;
import com.playdata.eungae.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityBoardDto {
	private Long communityBoardSeq;
	private Long memberSeq;
	private String title;
	private String content;
	private String author;
	private String date;
	private String thumbnailImageUrl;


	public static CommunityBoardDto toDto(CommunityBoard entity) {
		return CommunityBoardDto.builder()
			.communityBoardSeq(entity.getCommunityBoardSeq())
			.memberSeq(entity.getMember().getMemberSeq()) // Member 객체에서 ID 추출
			.title(entity.getTitle())
			.content(entity.getContent())
			.build();
	}
}