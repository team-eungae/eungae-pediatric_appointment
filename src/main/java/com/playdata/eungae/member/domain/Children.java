package com.playdata.eungae.member.domain;

import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.member.dto.ChildrenDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "children")
@Entity
public class Children extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long childrenSeq;

	@Column(nullable = false)
	private long memberSeq;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String birthDate;

	 @Column(nullable = false)
	 private String gender;

	@Column
	private String profileImage;
	
	//사진 경로 필드
	@Column
	private String photoPath;

	@Column
	private String etcInfo;

	@Lob
	@Column
	private byte[] photoContent;

	public static Children from(ChildrenDto dto) {
		return Children.builder()
			.memberSeq(dto.getMemberSeq())
			.name(dto.getName())
			.birthDate(dto.getBirthDate())
			.gender(dto.getGender())
			.profileImage(dto.getProfileImage())
			.etcInfo(dto.getEtcInfo())
			.photoContent(dto.getPhotoContent())
			.build();
	}
	public void updateFromDto(ChildrenDto childrenDto) {
		this.memberSeq = childrenDto.getMemberSeq();
		this.name = childrenDto.getName();
		this.birthDate = childrenDto.getBirthDate();
		this.gender = childrenDto.getGender();
		this.profileImage = childrenDto.getProfileImage();
		this.photoPath = childrenDto.getPhotoPath();
		this.etcInfo = childrenDto.getEtcInfo();
		this.photoContent = childrenDto.getPhotoContent();
	}
}
