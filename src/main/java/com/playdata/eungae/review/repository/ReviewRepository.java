package com.playdata.eungae.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.review.domain.Review;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
