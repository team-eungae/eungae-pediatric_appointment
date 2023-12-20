package com.playdata.eungae.member.domain;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "children")
public class Children {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long childrenSeq;

	@Column(nullable = false)
	private Long memberSeq;
	private String name;
	private String birthDate;
	private char gender;
	private String profileImage;

	private String etcInfo;

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	// Getters and Setters
}