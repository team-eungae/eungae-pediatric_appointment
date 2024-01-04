package com.playdata.eungae.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playdata.eungae.member.domain.Children;

@Repository
public interface ChildrenRepository extends JpaRepository<Children, Long> {

	}

