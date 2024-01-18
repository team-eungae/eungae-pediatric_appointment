package com.playdata.eungae.article.controller;

import com.playdata.eungae.article.dto.CommunityBoardDto;
import com.playdata.eungae.article.service.CommunityBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String listArticles(@AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        model.addAttribute("posts", communityBoardService.getAllCommunityBoards(userDetails.getUsername()));
        return "contents/community/community-list";
    }

    @GetMapping("/articles/post")
    public String showArticleForm(Model model) {

        return "contents/community/community-write";
    }

    @PostMapping("/articles/post")
    public String postArticle(@ModelAttribute CommunityBoardDto communityBoardDto,
                              @AuthenticationPrincipal UserDetails userDetails) {
        communityBoardService.createCommunityBoard(communityBoardDto, userDetails.getUsername());
        return "redirect:/articles";
    }

    @GetMapping("/articles/{article-seq}")
    public String viewArticle(@PathVariable("article-seq") Long id,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        model.addAttribute("post", communityBoardService.getCommunityBoardById(id, userDetails.getUsername()));
        return "contents/community/community-post-details";
    }

    @GetMapping("/articles/{communityBoardSeq}/form")
    public String editArticleForm(@PathVariable Long communityBoardSeq,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        model.addAttribute("post", communityBoardService.getCommunityBoardById(communityBoardSeq, userDetails.getUsername()));
        return "contents/community/community-edit";
    }

    @PostMapping("/articles/update/{communityBoardSeq}")
    public String updateArticle(@PathVariable Long communityBoardSeq,
                                @ModelAttribute CommunityBoardDto communityBoardDto,
                                @AuthenticationPrincipal UserDetails userDetails) {
        communityBoardService.updateCommunityBoard(communityBoardSeq, communityBoardDto, userDetails.getUsername());
        return "redirect:/articles";
    }

    @PostMapping("/articles/delete/{communityBoardSeq}")
    public String deleteArticle(@PathVariable Long communityBoardSeq,
                                @AuthenticationPrincipal UserDetails userDetails) {
        communityBoardService.deleteCommunityBoard(communityBoardSeq, userDetails.getUsername());
        return "redirect:/articles";
    }
}
