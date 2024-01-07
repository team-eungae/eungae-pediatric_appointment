package com.playdata.eungae.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.member.domain.Children;

@Repository
public interface ChildrenRepository extends JpaRepository<Children, Long> {
	Optional<List<Children>> findAllByMemberSeq(long MemberSeq);
}
