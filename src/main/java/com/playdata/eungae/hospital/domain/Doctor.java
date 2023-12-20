
package com.playdata.eungae.hospital.domain;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {

	@Id @GeneratedValue
	private Long doctorSeq;

	private String name;
	private String status;
	private Long treatmentPossible;
	private String profileImage;
/*
    메타데이터로 처리하죵
    방법은 다같이 알아봐영
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
*/

}
