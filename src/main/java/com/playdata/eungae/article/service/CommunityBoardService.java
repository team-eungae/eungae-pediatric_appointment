package com.playdata.eungae.article.service;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommunityBoardService {

	private final CommunityBoardRepository communityBoardRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public Long createCommunityBoard(CommunityBoardDto communityBoardDto, String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalStateException("Email does not exist: " + email));

		CommunityBoard communityBoard = CommunityBoard.builder()
			.member(member)
			.title(communityBoardDto.getTitle())
			.content(communityBoardDto.getContent())
			.build();

		communityBoardRepository.save(communityBoard);
		return communityBoard.getCommunityBoardSeq();
	}

	@Transactional(readOnly = true)
	public CommunityBoardDto getCommunityBoardById(Long id, String currentUserEmail) {
		CommunityBoard communityBoard = communityBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
		return CommunityBoardDto.toDto(communityBoard, currentUserEmail);
	}

	@Transactional
	public void updateCommunityBoard(Long communityBoardSeq, CommunityBoardDto communityBoardDto, String userEmail) {
		CommunityBoard communityBoard = communityBoardRepository.findById(communityBoardSeq)
			.orElseThrow(() -> new IllegalStateException("Article does not exist: " + communityBoardSeq));

		if (!communityBoard.getMember().getEmail().equals(userEmail)) {
			throw new IllegalStateException("You have no authority to modify.");
		}

		communityBoard.setTitle(communityBoardDto.getTitle());
		communityBoard.setContent(communityBoardDto.getContent());
	}

	@Transactional
	public void deleteCommunityBoard(Long communityBoardSeq, String userEmail) {
		CommunityBoard communityBoard = communityBoardRepository.findById(communityBoardSeq)
			.orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));

		if (!communityBoard.getMember().getEmail().equals(userEmail)) {
			throw new IllegalStateException("You have no authority to modify.");
		}

		communityBoardRepository.delete(communityBoard);
	}

	@Transactional(readOnly = true)
	public List<CommunityBoardDto> getAllCommunityBoards(String currentUserEmail) {
		return communityBoardRepository.findAll().stream()
			.map(board -> CommunityBoardDto.toDto(board, currentUserEmail))
			.collect(Collectors.toList());
	}

	private File convertToFile(MultipartFile multipartFile) throws IOException {
		File convFile = new File(multipartFile.getOriginalFilename());
		multipartFile.transferTo(convFile);
		return convFile;
	}

}