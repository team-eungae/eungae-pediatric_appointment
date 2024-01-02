package com.playdata.eungae.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberUpdateRequestDto {
    private String email;    //이메일
    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private String zipCode; //우편번호
}
