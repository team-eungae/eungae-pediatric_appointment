package com.playdata.eungae.article.dto;

import com.playdata.eungae.article.domain.Notice;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class NoticeListResponseDto {

    private Long noticeSeq;
    private String title;
    private String content;
    private String createdAt;
    private String modifiedAt;

    public static NoticeListResponseDto toDto(Notice notice) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return NoticeListResponseDto.builder()
                .noticeSeq(notice.getNoticeSeq())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(dateTimeFormatter.format(notice.getCreatedAt()))
                .modifiedAt(dateTimeFormatter.format(notice.getCreatedAt()))
                .build();
    }

}
