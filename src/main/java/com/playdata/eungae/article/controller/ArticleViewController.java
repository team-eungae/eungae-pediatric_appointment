package com.playdata.eungae.article.controller;

import java.io.IOException;

import com.playdata.eungae.article.dto.CommunityBoardDto;
import com.playdata.eungae.article.service.CommunityBoardService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ArticleViewController {

	private final CommunityBoardService communityBoardService;


	@GetMapping("/articles")
	public String listArticles(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		String email = (userDetails != null) ? userDetails.getUsername() : null;
		model.addAttribute("posts", communityBoardService.getAllCommunityBoards(email));
		return "contents/community/community-list";
	}

	@GetMapping("/articles/post")
	public String showArticleForm(Model model) {

		return "contents/community/community-write";
	}

	@PostMapping("/articles/post")
	public String postArticle(@ModelAttribute CommunityBoardDto communityBoardDto,
								Authentication authentication)  {
		String email = ((UserDetails)authentication.getPrincipal()).getUsername();
		communityBoardService.createCommunityBoard(communityBoardDto, email);
		return "redirect:/articles";
	}

	@GetMapping("/articles/{article-seq}")
	public String viewArticle(@PathVariable("article-seq") Long id, Model model,
		@AuthenticationPrincipal UserDetails userDetails) {
		String email = (userDetails != null) ? userDetails.getUsername() : null;
		CommunityBoardDto post = communityBoardService.getCommunityBoardById(id, email);
		model.addAttribute("post", post);
		return "contents/community/community-post-details";
	}
	@GetMapping("/articles/{communityBoardSeq}/form")
	public String editArticleForm(@PathVariable Long communityBoardSeq, Model model,
		@AuthenticationPrincipal UserDetails userDetails) {
		String email = (userDetails != null) ? userDetails.getUsername() : null;
		CommunityBoardDto post = communityBoardService.getCommunityBoardById(communityBoardSeq, email);
		model.addAttribute("post", post);
		return "contents/community/community-edit";
	}

	@PostMapping("/articles/update/{communityBoardSeq}")
	public String updateArticle(@PathVariable Long communityBoardSeq,
		@ModelAttribute CommunityBoardDto communityBoardDto,
		@AuthenticationPrincipal UserDetails userDetails) {
		String email = (userDetails != null) ? userDetails.getUsername() : null;
		communityBoardService.updateCommunityBoard(communityBoardSeq, communityBoardDto, email);
		return "redirect:/articles";
	}

	@PostMapping("/articles/delete/{communityBoardSeq}")
	public String deleteArticle(@PathVariable Long communityBoardSeq, Authentication authentication) {
		String email = ((UserDetails)authentication.getPrincipal()).getUsername();
		CommunityBoardDto post = communityBoardService.getCommunityBoardById(communityBoardSeq, email);
		if (post == null || !email.equals(post.getMemberEmail())) {
			return "redirect:/articles"; // 권한 없음 처리
		}
		communityBoardService.deleteCommunityBoard(communityBoardSeq, email);
		return "redirect:/articles"; // 삭제 후 게시글 목록으로 리디렉션
	}
}
