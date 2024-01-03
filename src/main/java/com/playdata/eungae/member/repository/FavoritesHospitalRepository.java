package com.playdata.eungae.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playdata.eungae.member.domain.FavoritesHospital;

public interface FavoritesHospitalRepository extends JpaRepository<FavoritesHospital, Long> {
}
