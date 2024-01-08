package com.playdata.eungae.member.dto;

import org.springframework.web.multipart.MultipartFile;

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
	private MultipartFile photoContent;

	public static ChildrenDto from(Children entity) {
		return ChildrenDto.builder()
			.childrenSeq(entity.getChildrenSeq())
			.name(entity.getName())
			.birthDate(entity.getBirthDate())
			.gender(entity.getGender())
			.profileImage(entity.getProfileImage())
			.photoPath(entity.getPhotoPath())
			.etcInfo(entity.getEtcInfo())
			.build();
	}
}