package com.playdata.eungae.article.service;

import java.util.List;
import java.util.stream.Collectors;

import com.playdata.eungae.article.domain.CommunityBoard;
import com.playdata.eungae.article.dto.CommunityBoardDto;
import com.playdata.eungae.article.repository.CommunityBoardRepository;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityBoardService {

	private final CommunityBoardRepository communityBoardRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public Long createCommunityBoard(CommunityBoardDto communityBoardDto, String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalStateException("해당 이메일의 사용자가 존재하지 않습니다: " + email));

		CommunityBoard communityBoard = CommunityBoard.builder()
			.member(member)
			.title(communityBoardDto.getTitle())
			.content(communityBoardDto.getContent())
			.build();

		communityBoardRepository.save(communityBoard);
		return communityBoard.getCommunityBoardSeq();
	}
	@Transactional
	public void updateCommunityBoard(Long communityBoardSeq, CommunityBoardDto communityBoardDto) {
		CommunityBoard communityBoard = communityBoardRepository.findById(communityBoardSeq)
			.orElseThrow(() -> new IllegalStateException("해당 게시글이 존재하지 않습니다: " + communityBoardSeq));

		communityBoard.setTitle(communityBoardDto.getTitle());
		communityBoard.setContent(communityBoardDto.getContent());
	}

		@Transactional
	public void deleteCommunityBoard(Long communityBoardSeq) {
		CommunityBoard communityBoard = communityBoardRepository.findById(communityBoardSeq)
			.orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
		communityBoardRepository.delete(communityBoard);
	}

	@Transactional(readOnly = true)
	public List<CommunityBoardDto> getAllCommunityBoards() {
		return communityBoardRepository.findAll().stream()
			.map(CommunityBoardDto::toDto)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CommunityBoardDto getCommunityBoardById(Long id) {
		CommunityBoard communityBoard = communityBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
		return CommunityBoardDto.toDto(communityBoard);
	}

}