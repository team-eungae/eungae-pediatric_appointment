package com.playdata.eungae.article.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.article.domain.CommunityBoard;
import com.playdata.eungae.article.dto.CommunityBoardDto;
import com.playdata.eungae.article.repository.CommunityBoardRepository;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityBoardService {

	private final CommunityBoardRepository communityBoardRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void createCommunityBoard(CommunityBoardDto communityBoardDto, String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalStateException("Email does not exist: " + email));

		CommunityBoard communityBoard = CommunityBoardDto.toEntity(communityBoardDto, member);

		communityBoardRepository.save(communityBoard);
	}

	@Transactional(readOnly = true)
	public CommunityBoardDto getCommunityBoardById(Long communityBoardSeq, String currentUserEmail) {
		CommunityBoard communityBoard = communityBoardRepository.findByIdWithMember(communityBoardSeq)
			.orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
		return CommunityBoardDto.toDto(communityBoard, currentUserEmail);
	}

	@Transactional
	public void updateCommunityBoard(
		Long communityBoardSeq,
		CommunityBoardDto communityBoardDto,
		String userEmail
	) {
		CommunityBoard communityBoard = communityBoardRepository.findByIdWithMember(communityBoardSeq)
			.orElseThrow(() -> new IllegalStateException("Article does not exist: " + communityBoardSeq));

		if (!communityBoard.getMember().getEmail().equals(userEmail)) {
			throw new IllegalStateException("You have no authority to modify.");
		}

		communityBoard.setTitle(communityBoardDto.getTitle());
		communityBoard.setContent(communityBoardDto.getContent());
	}

	@Transactional
	public void deleteCommunityBoard(Long communityBoardSeq, String userEmail) {
		CommunityBoard communityBoard = communityBoardRepository.findByIdWithMember(communityBoardSeq)
			.orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));

		if (!communityBoard.getMember().getEmail().equals(userEmail)) {
			throw new IllegalStateException("You have no authority to modify.");
		}

		communityBoard.deleted();
	}

	@Transactional(readOnly = true)
	public List<CommunityBoardDto> getCommunityBoards(String currentUserEmail) {
		return communityBoardRepository.findAllWithMember()
			.stream()
			.map(board -> CommunityBoardDto.toDto(board, currentUserEmail))
			.collect(Collectors.toList());
	}

}