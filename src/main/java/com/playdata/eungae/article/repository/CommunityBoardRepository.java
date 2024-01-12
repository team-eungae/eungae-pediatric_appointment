package com.playdata.eungae.article.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.article.domain.CommunityBoard;
import com.playdata.eungae.member.domain.Member;

@Repository
public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long> {

}
