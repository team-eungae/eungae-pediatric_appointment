package com.playdata.eungae.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.playdata.eungae.member.domain.FavoritesHospital;

public interface FavoritesHospitalRepository extends JpaRepository<FavoritesHospital, Long> {

	@Query ("select f from FavoritesHospital f"
		+ " join fetch f.hospital"
		+ " join fetch f.member"
		+ " where f.member.email = :userEmail")
	List<FavoritesHospital> getByUserEmail(@Param("userEmail") String userEmail);
}
