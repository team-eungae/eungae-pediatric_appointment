package com.playdata.eungae.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.review.domain.Review;
import com.playdata.eungae.review.dto.ResponseReviewDto;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("select r from Review r"
		+ " join fetch r.member m"
		+ " join fetch r.hospital h"
		+ " where r.hospital.hospitalSeq = :hospitalSeq")
	Page<Review> findAllWithMember(Pageable pageConfig, @Param("hospitalSeq") Long hospitalSeq);


	Optional<List<Review>> findAllByHospitalHospitalSeq(Long hospitalSeq);

}
