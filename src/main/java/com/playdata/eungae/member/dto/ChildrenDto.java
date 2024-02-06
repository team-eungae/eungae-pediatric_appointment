package com.playdata.eungae.member.dto;

import com.playdata.eungae.member.domain.Children;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenDto {
	private Long childrenSeq;
	private long memberSeq;
	private String name;
	private String birthDate;
	private String gender;
	private String profileImage;
	private String etcInfo;

	public static ChildrenDto toDto(Children entity) {
		return ChildrenDto.builder()
			.childrenSeq(entity.getChildrenSeq())
			.name(entity.getName())
			.birthDate(entity.getBirthDate())
			.gender(entity.getGender())
			.profileImage(entity.getProfileImage())
			.etcInfo(entity.getEtcInfo())
			.build();
	}
}