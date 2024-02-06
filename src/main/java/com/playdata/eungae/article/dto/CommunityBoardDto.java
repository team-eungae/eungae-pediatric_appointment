package com.playdata.eungae.article.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.playdata.eungae.article.domain.CommunityBoard;
import com.playdata.eungae.member.domain.Member;

import jakarta.validation.constraints.NotBlank;
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
	@NotBlank(message = "제목입력은 필수입니다.")
	private String title;
	@NotBlank(message = "내용입력은 필수입니다.")
	private String content;
	private LocalDateTime createdDate;
	private String memberEmail;
	private String memberName;
	private boolean isOwner;

	public static CommunityBoardDto toDto(CommunityBoard entity, String currentUserEmail) {
		return CommunityBoardDto.builder()
			.communityBoardSeq(entity.getCommunityBoardSeq())
			.memberSeq(entity.getMember().getMemberSeq())
			.title(entity.getTitle())
			.content(entity.getContent())
			.createdDate(entity.getCreatedAt())
			.memberEmail(entity.getMember().getEmail())
			.memberName(entity.getMember().getName())
			.isOwner(entity.getMember().getEmail().equals(currentUserEmail)) // 소유자 여부 설정
			.build();
	}

	public static CommunityBoard toEntity(CommunityBoardDto communityBoardDto, Member member) {
		return CommunityBoard.builder()
			.member(member)
			.title(communityBoardDto.getTitle())
			.content(communityBoardDto.getContent())
			.build();
	}

}