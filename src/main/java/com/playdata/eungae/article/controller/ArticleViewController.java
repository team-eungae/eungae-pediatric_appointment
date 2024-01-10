package com.playdata.eungae.article.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playdata.eungae.article.dto.CommunityBoardDto;
import com.playdata.eungae.article.service.CommunityBoardService;

@Controller
@RequestMapping("/article")
public class ArticleViewController {

	private final CommunityBoardService communityBoardService;

	@Autowired
	public ArticleViewController(CommunityBoardService communityBoardService) {
		this.communityBoardService = communityBoardService;
	}

	@GetMapping("/list")
	public String communityList(Model model) {
		List<CommunityBoardDto> posts = communityBoardService.readAllCommunityBoards();
		model.addAttribute("posts",posts);
		return "contents/community/community-list";
	}

	@GetMapping("/details")
	public String communityDetail() {
		return "contents/community/community-post-details";
	}

	@GetMapping("/post")
	public String communityWrite() {
		return "contents/community/community-write";
	}

	@PostMapping("/post")
	public String postCommunityBoard(@ModelAttribute CommunityBoardDto communityBoardDto) {
		communityBoardService.createCommunityBoard(communityBoardDto);
		return "redirect:/article/list"; // 게시글 목록 페이지로 리디렉션
	}
}
