package com.playdata.eungae.member.domain;

import org.hibernate.annotations.DynamicInsert;

import com.playdata.eungae.base.BaseEntity;

import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.*;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "member")
@Entity
@Setter
public class Member extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long memberSeq;

	@Column(nullable = false, unique = true, updatable = false)
	private String email;

	@Column(nullable = false, length = 30)
	private String password;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false, length = 15)
	private String phoneNumber;

	@Column(nullable = false)
	private String birthDate;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String addressDetail;

	@Column(nullable = false)
	private String zipCode;

	private Integer xCoordinate;

	private Integer yCoordinate;

	//0이면 일반 로그인 1이면 카카오 로그인
	@Column(columnDefinition = "varchar(1) default '0'")
	private boolean kakaoCheck;

	//회원정보수정
	public void updateMemberDetails(MemberUpdateRequestDto updateRequestDto){
		this.setName(updateRequestDto.getName());
		this.setPhoneNumber(updateRequestDto.getPhoneNumber());
		this.setAddress(updateRequestDto.getAddress());
		this.setAddressDetail(updateRequestDto.getAddressDetail());
		this.setZipCode(updateRequestDto.getZipCode());
	}
}
