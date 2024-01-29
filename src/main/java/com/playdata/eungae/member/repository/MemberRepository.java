package com.playdata.eungae.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	@Query("select m from Member m"
		+ " where m.email = :email"
		+ " and m.deleteYN = 'N'")
	Optional<Member> findByEmail(@Param("email") String email);
}
