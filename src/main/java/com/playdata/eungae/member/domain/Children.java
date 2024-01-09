package com.playdata.eungae.member.domain;

import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.member.dto.ChildrenDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	// Member와 children의 연관관계를 일부로 시퀀스값으로 준 이유가 있을까요??
	// @Column(nullable = false)
	// private long memberSeq;

	// 제 생각엔 객체간 연관관계를 맺어주는것이 좋을것같습니다.
	// 코드 리뷰 후 주석을 삭제하면 될것같습니다.
	@ManyToOne
	@JoinColumn(name = "member_seq")
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

	public static Children from(ChildrenDto dto) {
		return Children.builder()
			// Children Entity를 생성할때 memberSeq만 할당해오는 것이 아닌
			// dto에 있는 memberSeq를 바탕으로 Member Entity를 조회해 와 연관관계를 맺어주는 방법이 맞는것 같습니다.
			// .memberSeq(dto.getMemberSeq())
			.name(dto.getName())
			.birthDate(dto.getBirthDate())
			.gender(dto.getGender())
			.profileImage(dto.getProfileImage())
			.photoPath(dto.getPhotoPath())
			.etcInfo(dto.getEtcInfo())
			.build();
	}
}