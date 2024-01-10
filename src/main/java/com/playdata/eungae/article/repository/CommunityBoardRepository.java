package com.playdata.eungae.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.article.domain.CommunityBoard;

@Repository
public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long> {
}
