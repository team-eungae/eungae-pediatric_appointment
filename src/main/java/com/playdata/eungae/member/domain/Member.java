package com.playdata.eungae.member.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.playdata.eungae.appointment.domain.Appointment;
import com.playdata.eungae.base.BaseEntity;
import com.playdata.eungae.member.dto.MemberUpdateRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "member")
@Entity
public class Member extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long memberSeq;

	@Column(nullable = false, unique = true, updatable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false, length = 15)
	private String phoneNumber;

	@Column(nullable = false)
	private String birthDate;

	@Column(nullable = false)
	private String address;

	private String addressDetail;

	@Column(nullable = false)
	private String zipCode;

	private Integer xCoordinate;

	private Integer yCoordinate;

	@Column(columnDefinition = "varchar(5) default '0'")
	private boolean kakaoCheck;

	@Builder.Default
	@OneToMany(mappedBy = "member")
	private List<Children> children = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "member")
	private List<Appointment> appointments = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "member")
	private List<FavoritesHospital> favoritesHospitals = new ArrayList<>();

	public void updateMemberDetails(MemberUpdateRequestDto updateRequestDto) {
		this.name = updateRequestDto.getName();
		this.phoneNumber = updateRequestDto.getPhoneNumber();
		this.address = updateRequestDto.getAddress();
		this.addressDetail = updateRequestDto.getAddressDetail();
		this.zipCode = updateRequestDto.getZipCode();
	}

	public void addChildren(Children children) {
		this.children.add(children);
	}
}
