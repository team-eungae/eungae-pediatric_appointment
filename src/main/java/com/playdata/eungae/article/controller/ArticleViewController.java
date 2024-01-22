package com.playdata.eungae.article.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.playdata.eungae.article.dto.CommunityBoardDto;
import com.playdata.eungae.article.service.CommunityBoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ArticleViewController {

    private final CommunityBoardService communityBoardService;

	@GetMapping("/articles")
	public String listArticles(
		Model model,
		@AuthenticationPrincipal UserDetails userDetails
	) {
		model.addAttribute("posts", communityBoardService.getCommunityBoards(userDetails.getUsername()));
		return "contents/community/community-list";
	}

	@GetMapping("/articles/post")
	public String showArticleForm(Model model) {
		model.addAttribute("communityBoardDto", new CommunityBoardDto());
		return "contents/community/community-write";
	}

	@PostMapping("/articles/post")
	public String postArticle(
		@AuthenticationPrincipal UserDetails userDetails,
		@Valid @ModelAttribute CommunityBoardDto communityBoardDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return "contents/community/community-write";
		}
		communityBoardService.createCommunityBoard(communityBoardDto, userDetails.getUsername());
		return "redirect:/articles";
	}

	@GetMapping("/articles/{article-seq}")
	public String viewArticle(
		@PathVariable("article-seq") Long id,
		Model model,
		@AuthenticationPrincipal UserDetails userDetails
	) {
		model.addAttribute("post", communityBoardService.getCommunityBoardById(id, userDetails.getUsername()));
		return "contents/community/community-post-details";
	}

	@GetMapping("/articles/{communityBoardSeq}/form")
	public String editArticleForm(
		@PathVariable Long communityBoardSeq,
		@AuthenticationPrincipal UserDetails userDetails,
		Model model
	) {
		CommunityBoardDto communityBoardDto = communityBoardService.getCommunityBoardById(communityBoardSeq, userDetails.getUsername());
		model.addAttribute("communityBoardDto", communityBoardDto);
		return "contents/community/community-edit";
	}

	@PostMapping("/articles/update")
	public String updateArticle(
		@AuthenticationPrincipal UserDetails userDetails,
		@Valid @ModelAttribute CommunityBoardDto communityBoardDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return "contents/community/community-edit";
		}
		communityBoardService.updateCommunityBoard(communityBoardDto.getCommunityBoardSeq(), communityBoardDto, userDetails.getUsername());
		return "redirect:/articles";
	}
  
	@PostMapping("/articles/delete/{communityBoardSeq}")
	public String deleteArticle(
		@PathVariable Long communityBoardSeq,
		@AuthenticationPrincipal UserDetails userDetails
	) {
		communityBoardService.deleteCommunityBoard(communityBoardSeq, userDetails.getUsername());
		return "redirect:/articles";
	}
}
