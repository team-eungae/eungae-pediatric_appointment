package com.playdata.eungae.member.domain;

import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.member.dto.ChildrenDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "children")
public class Children extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long childrenSeq;

	@JoinColumn(name = "member_seq")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String birthDate;

	@Column(nullable = false)
	private String gender;

	@Column
	private String etcInfo;

	@Column
	private String profileImage;

	@Column
	private String photoPath;

	@Lob
	@Column
	private byte[] photoContent;

	@Column
	private String imageMimeType;

	// 연관관계 편의 메서드
	public void setMember(Member member) {
		this.member = member;
		member.addChildren(this);
	}

	public static Children from(ChildrenDto dto) {
		return Children.builder()
			.name(dto.getName())
			.birthDate(dto.getBirthDate())
			.gender(dto.getGender())
			.profileImage(dto.getProfileImage())
			.photoPath(dto.getPhotoPath())
			.etcInfo(dto.getEtcInfo())
			.build();
	}
}