package com.playdata.eungae.article.controller;

import com.playdata.eungae.article.dto.NoticeListResponseDto;
import com.playdata.eungae.article.dto.NoticeResponseDto;
import com.playdata.eungae.article.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/notice")
public class NoticeViewController {
    private final NoticeService noticeService;

    @GetMapping
    public String noticeList(Model model) {
        List<NoticeListResponseDto> noticeList = noticeService.findByNoticeList();
        model.addAttribute("noticeList", noticeList);
        return "contents/notice/notice-list";
    }

    @GetMapping("/details/{noticeSeq}")
    public String noticeDetail(@PathVariable Long noticeSeq, Model model) {
        NoticeResponseDto noticeResponseDto = noticeService.findByNoticeId(noticeSeq);
        model.addAttribute("notice", noticeResponseDto);
        return "contents/notice/notice-details";
    }

}