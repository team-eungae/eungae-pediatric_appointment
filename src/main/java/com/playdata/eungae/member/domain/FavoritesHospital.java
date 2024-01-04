package com.playdata.eungae.member.domain;

import java.util.List;

import org.hibernate.annotations.Struct;

import com.playdata.eungae.hospital.domain.Hospital;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "favorites_hospital")
@Entity
@Builder
public class FavoritesHospital {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long favoritesHospitalSeq;

	@Setter
	@ManyToOne
	@JoinColumn(name = "member_seq")
	private Member member;

	@Setter
	@ManyToOne
	@JoinColumn(name = "hospital_seq")
	private Hospital hospital;

}
