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
public class MemberUpdateResponseDto {
    private Long memberSeq;
    private String email;    //이메일
    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private String zipCode;

    //빌더
    public static MemberUpdateResponseDto toDto(Member member){
        return MemberUpdateResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .addressDetail(member.getAddressDetail())
                .zipCode(member.getZipCode())
                .build();
    }
}
