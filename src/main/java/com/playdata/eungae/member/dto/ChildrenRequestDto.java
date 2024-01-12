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
public class ChildrenRequestDto {
	private Long childrenSeq;
	private long memberSeq;
	private String name;
	private String birthDate;
	private String gender;
	private String etcInfo;

	public static Children toEntity(ChildrenRequestDto childrenRequestDto, String profileImage) {
		return Children.builder()
			.childrenSeq(childrenRequestDto.getChildrenSeq())
			.name(childrenRequestDto.getName())
			.birthDate(childrenRequestDto.getBirthDate())
			.gender(childrenRequestDto.getGender())
			.profileImage(profileImage)
			.etcInfo(childrenRequestDto.getEtcInfo())
			.build();
	}
}