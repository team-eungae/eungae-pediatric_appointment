package com.playdata.eungae.review.repository;

import org.springframework.stereotype.Repository;

import com.playdata.eungae.review.domain.Review;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

	private final EntityManager em;

	@Override
	public void createReview(Review review) {
		em.persist(review);
	}
}
