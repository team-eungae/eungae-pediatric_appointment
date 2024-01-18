package com.playdata.eungae.article.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.playdata.eungae.article.domain.CommunityBoard;


@Repository
public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long> {

	@Query("select c from CommunityBoard c"
		+ " join fetch c.member"
		+ " order by c.communityBoardSeq desc ")
	List<CommunityBoard> findAllWithMember();

}
