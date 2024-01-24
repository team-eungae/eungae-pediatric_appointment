package com.playdata.eungae.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("select r from Review r"
		+ " join fetch r.member m"
		+ " join fetch r.hospital h"
		+ " where r.hospital.hospitalSeq = :hospitalSeq")
	Page<Review> findAllWithMember(Pageable pageConfig, @Param("hospitalSeq") Long hospitalSeq);


	@Query("select r from Review r"
		+ " join fetch r.member"
		+ " join fetch r.hospital"
		+ " where r.hospital.hospitalSeq = :hospitalSeq"
		+ " and r.deleteYN != 'Y'"
		+ " order by r.reviewSeq desc ")
	List<Review> findAllByHospitalHospitalSeq(@Param("hospitalSeq") Long hospitalSeq);

	@Query("select r from Review r"
		+ " join fetch r.member"
		+ " join fetch r.hospital"
		+ " where r.member.email = :email"
		+ " and r.deleteYN != 'Y'"
		+ " order by r.reviewSeq desc ")
	List<Review> findReviewsByMemberEmail(@Param("email") String memberEmail);
}
