package com.playdata.eungae.member.controller;

import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.MemberFindResponseDto;
import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;
import com.playdata.eungae.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my")
@RequiredArgsConstructor
@Slf4j
public class MemberViewController {

	@GetMapping("/records")
	public String medicalRecordList() {
		return "contents/member/medical-records";
	}

	@GetMapping("/records/1")
	public String medicalRecordsDetails() {
		return "contents/member/medical-records-details";
	}

	@GetMapping("/profile")
	public String myPage() {
		return "contents/member/my-page";
	}

    @GetMapping("/profile/{memberSeq}")
    public String myPage(@PathVariable Long memberSeq, Model model) {
        log.info(memberSeq+"88888888888888888888888888");
        MemberFindResponseDto memberFindResponseDto = memberService.findById(memberSeq);
        model.addAttribute("member", memberFindResponseDto);
        return "contents/member/my-page";
    }

    @GetMapping("/profile/form/{memberSeq}")
    public String updateProfile(@PathVariable Long memberSeq, Model model) {
        model.addAttribute("member", memberService.updateFindById(memberSeq));
        return "contents/member/my-page-form";
    }

    @GetMapping("/appointments")
    public String myReservationList() {
        return "contents/member/my-reservations";
    }

	@GetMapping("/hospitals")
	public String regularHospitals() {
		return "contents/member/regular-hospital";
	}

	@GetMapping("/children")
	public String myChildren() {
		return "contents/member/my-children";
	}

	@GetMapping("/children/form")
	public String addMyChildren() {
		return "contents/member/my-children-add";
	}

    @GetMapping("/children/form")
    public String addMyChildren() {
        return "contents/member/my-children-add";
    }

}
