package com.playdata.eungae.review.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review")
public class ReviewViewController {
    @GetMapping("/post")
    public String reviewWrite() {
        return "contents/member/review-write";
    }
}
