package com.playdata.eungae.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.member.domain.Children;

@Repository
public interface ChildrenRepository extends JpaRepository<Children, Long> {

	@Query("select c from Children c"
		+ " where c.member.memberSeq = :memberSeq"
		+ " and c.deleteYN != 'Y'")
	List<Children> findAllByMemberMemberSeq(@Param("memberSeq") long MemberSeq);
}
