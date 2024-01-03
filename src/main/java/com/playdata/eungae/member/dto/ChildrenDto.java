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
	private String photoPath;
	private String etcInfo;
	private byte[] photoContent;

	public static ChildrenDto from(Children children) {
		ChildrenDto dto = ChildrenDto.builder()
			.childrenSeq(children.getChildrenSeq())
			.memberSeq(children.getMemberSeq())
			.name(children.getName())
			.birthDate(children.getBirthDate())
			.gender(children.getGender())
			.profileImage(children.getProfileImage())
			.photoPath(children.getPhotoPath())
			.etcInfo(children.getEtcInfo())
			.photoContent(children.getPhotoContent()) // 원본 바이너리 데이터
			.build();

		return dto;
	}
}
