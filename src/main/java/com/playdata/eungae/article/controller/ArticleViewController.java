package com.playdata.eungae.article.controller;

import com.playdata.eungae.article.dto.CommunityBoardDto;
import com.playdata.eungae.article.service.CommunityBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ArticleViewController {

	private final CommunityBoardService communityBoardService;


	@Autowired
	public ArticleViewController(CommunityBoardService communityBoardService) {
		this.communityBoardService = communityBoardService;
	}

	@GetMapping("/articles")
	public String listArticles(Model model) {
		model.addAttribute("posts", communityBoardService.getAllCommunityBoards());
		return "contents/community/community-list";
	}

	@GetMapping("/articles/post")
	public String showArticleForm(Model model) {

		return "contents/community/community-write";
	}

	@PostMapping("/articles/post")
	public String postArticle(@ModelAttribute CommunityBoardDto communityBoardDto, Authentication authentication) {
		String email = ((UserDetails) authentication.getPrincipal()).getUsername();
		communityBoardService.createCommunityBoard(communityBoardDto, email);
		return "redirect:/articles";
	}
	@GetMapping("/articles/{article-seq}")
	public String viewArticle(@PathVariable("article-seq") Long id, Model model) {
		model.addAttribute("post", communityBoardService.getCommunityBoardById(id));
		return "contents/community/community-post-details";
	}

	@GetMapping("/articles/{communityBoardSeq}/form")
	public String editArticleForm(@PathVariable Long communityBoardSeq, Model model) {
		model.addAttribute("post", communityBoardService.getCommunityBoardById(communityBoardSeq));
		return "contents/community/community-edit";
	}

	@PostMapping("/articles/update/{communityBoardSeq}")
	public String updateArticle(@PathVariable Long communityBoardSeq,
		@ModelAttribute CommunityBoardDto communityBoardDto,
		Authentication authentication) {
		communityBoardService.updateCommunityBoard(communityBoardSeq, communityBoardDto);
		return "redirect:/articles";
	}
	@PostMapping("/articles/delete/{communityBoardSeq}")
	public String deleteArticle(@PathVariable Long communityBoardSeq) {
		communityBoardService.deleteCommunityBoard(communityBoardSeq);
		return "redirect:/articles";
	}
}
