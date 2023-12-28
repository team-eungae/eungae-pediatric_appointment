package com.playdata.eungae.member.domain;

import com.playdata.eungae.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
@Entity
public class Member extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long memberSeq;

	@Column(nullable = false, unique = true)
	private String memberId;

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

	private boolean withdrawalStatus;
}
