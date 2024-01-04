package com.playdata.eungae.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.review.domain.Review;
import com.playdata.eungae.review.dto.ResponseReviewDto;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("select r from Review r"
		+ " join fetch r.member m"
	)
	Page<Review> findAllWithMember(Pageable pageConfig);
}
