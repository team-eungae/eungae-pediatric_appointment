package com.playdata.eungae.hospital.domain;

import com.playdata.eungae.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "hospital_image")
@Entity
public class HospitalImage extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long hospitalImageSeq;

	// @OneToMany
	// private Hospital hospitalSeq;

	@Column(nullable = false)
	private String originFileName;

	@Column(nullable = false)
	private String storeFileName;
}
