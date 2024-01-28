package com.playdata.eungae.article.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.playdata.eungae.article.domain.CommunityBoard;


@Repository
public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long> {

	@Query("select c from CommunityBoard c"
		+ " join fetch c.member"
		+ " where c.deleteYN = 'N'"
		+ " order by c.communityBoardSeq desc ")
	List<CommunityBoard> findAllWithMember();

	@Query("select c from CommunityBoard c"
		+ " join fetch c.member"
		+ " where c.communityBoardSeq = :communityBoardSeq"
		+ " and c.deleteYN = 'N'")
	Optional<CommunityBoard> findByIdWithMember(@Param("communityBoardSeq") Long communityBoardSeq);
}
