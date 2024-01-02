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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "children")
@Entity
@Builder
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

	@Column
	private String etcInfo;
}
