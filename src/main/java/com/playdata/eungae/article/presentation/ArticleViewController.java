package com.playdata.eungae.article.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleViewController {
	@GetMapping
	public String communityList(){
		return "contents/community/community-list";
	}

	@GetMapping("/details")
	public String communityDetail(){
		return "contents/community/community-post-details";
	}

	@GetMapping("/post")
	public String communityWrite(){
		return "contents/community/community-write";
	}

}
