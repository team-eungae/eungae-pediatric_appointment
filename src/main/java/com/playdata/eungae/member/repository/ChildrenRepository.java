package com.playdata.eungae.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.member.domain.Children;

@Repository
public interface ChildrenRepository extends JpaRepository<Children, Long> {
	List<Children> findAllByMemberMemberSeq(long MemberSeq);
}
