package com.playdata.eungae.article.service;

import com.playdata.eungae.article.domain.Notice;
import com.playdata.eungae.article.dto.NoticeResponseDto;
import com.playdata.eungae.article.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public NoticeResponseDto findByNoticeId(Long noticeSeq) {
        Notice notice = noticeRepository.findById(noticeSeq)
                .orElseThrow(() -> new IllegalStateException("This post doesn't exist."));
        return NoticeResponseDto.toDto(notice);
    }

    @Transactional(readOnly = true)
    public List<NoticeResponseDto> findByNoticeList() {
        List<Notice> notices = noticeRepository.findAll();
		return notices.stream()
            .map(NoticeResponseDto::toDto)
            .collect(Collectors.toList());
    }

}
