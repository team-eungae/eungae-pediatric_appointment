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
public class MemberUpdateRequestDto {
    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private String zipCode;

    public static Member toEntity(MemberUpdateRequestDto updateRequestDto) {
        return Member.builder()
                .name(updateRequestDto.getName())
                .phoneNumber(updateRequestDto.getPhoneNumber())
                .address(updateRequestDto.getAddress())
                .addressDetail(updateRequestDto.getAddressDetail())
                .zipCode(updateRequestDto.getZipCode())
                .build();
    }

}
