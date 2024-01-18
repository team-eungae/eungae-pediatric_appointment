package com.playdata.eungae.article.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.playdata.eungae.article.domain.CommunityBoard;

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
	private LocalDateTime createdDate;
	private String memberEmail;
	private boolean isOwner;

	public static CommunityBoardDto toDto(CommunityBoard entity, String currentUserEmail) {
		return CommunityBoardDto.builder()
			.communityBoardSeq(entity.getCommunityBoardSeq())
			.memberSeq(entity.getMember().getMemberSeq())
			.title(entity.getTitle())
			.content(entity.getContent())
			.createdDate(entity.getCreatedAt())
			.memberEmail(entity.getMember().getEmail())
			.isOwner(entity.getMember().getEmail().equals(currentUserEmail)) // 소유자 여부 설정
			.build();
	}

	public String getFormattedDate() {
		return this.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
}