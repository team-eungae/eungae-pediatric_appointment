package com.playdata.eungae.article.service;

import com.playdata.eungae.article.domain.CommunityBoard;
import com.playdata.eungae.article.dto.CommunityBoardDto;
import com.playdata.eungae.article.repository.CommunityBoardRepository;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommunityBoardService {

	private final CommunityBoardRepository communityBoardRepository;
	private final MemberRepository memberRepository;

	@Autowired
	public CommunityBoardService(MemberRepository memberRepository, CommunityBoardRepository communityBoardRepository) {
		this.memberRepository = memberRepository;
		this.communityBoardRepository = communityBoardRepository;
	}
	@Transactional
	public CommunityBoard createCommunityBoard(CommunityBoardDto dto) {
		Member member = memberRepository.findById(dto.getMemberSeq())
			.orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
		CommunityBoard communityBoard = CommunityBoard.from(dto);
		communityBoard.setMember(member);
		return communityBoardRepository.save(communityBoard);
	}

	@Transactional(readOnly = true)
	public Optional<CommunityBoardDto> readCommunityBoard(Long id) {
		return communityBoardRepository.findById(id)
			.map(CommunityBoardDto::from);
	}

	@Transactional(readOnly = true)
	public List<CommunityBoardDto> readAllCommunityBoards() {
		return communityBoardRepository.findAll().stream()
			.map(CommunityBoardDto::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public CommunityBoard updateCommunityBoard(Long id, CommunityBoardDto dto) {
		CommunityBoard existingBoard = communityBoardRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Board not found"));
		existingBoard.update(dto.getTitle(), dto.getContent());
		return communityBoardRepository.save(existingBoard);
	}

	@Transactional
	public void deleteCommunityBoard(Long id) {
		communityBoardRepository.deleteById(id);
	}
}