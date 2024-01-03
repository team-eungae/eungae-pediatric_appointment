package com.playdata.eungae.member.dto;

import com.playdata.eungae.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberFindResponseDto {
    private Long memberSeq;
    private String email;    //이메일
    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;

    //빌더
    public static MemberFindResponseDto toDto(Member member){
        return MemberFindResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .addressDetail(member.getAddressDetail())
                .build();
    }
}
