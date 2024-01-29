package com.playdata.eungae.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.playdata.eungae.appointment.dto.ResponseAppointmentDto;
import com.playdata.eungae.appointment.dto.ResponseDetailMedicalHistoryDto;
import com.playdata.eungae.appointment.dto.ResponseMedicalHistoryDto;
import com.playdata.eungae.appointment.service.AppointmentService;
import com.playdata.eungae.file.FileStore;
import com.playdata.eungae.file.ResultFileStore;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.ChildrenDto;
import com.playdata.eungae.member.dto.ChildrenRequestDto;
import com.playdata.eungae.member.dto.MemberInfoResponseDto;
import com.playdata.eungae.member.dto.ResponseFavoritesHospitalDto;
import com.playdata.eungae.member.repository.MemberRepository;
import com.playdata.eungae.member.service.ChildrenService;
import com.playdata.eungae.member.service.MemberService;
import com.playdata.eungae.review.dto.ResponseReviewDto;
import com.playdata.eungae.review.service.ReviewService;
import com.playdata.eungae.security.MemberUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/my")
@Controller
public class MemberViewController {

    private final MemberService memberService;
    private final AppointmentService appointmentService;
    private final ReviewService reviewService;
    private final ChildrenService childrenService;
    private final FileStore fileStore;
    private final MemberRepository memberRepository;


    @GetMapping("/records")
    public String medicalRecordList(
            Model model,
            @AuthenticationPrincipal UserDetails principal
            // @PathVariable int page,
    ) {
        List<ResponseMedicalHistoryDto> myMedicalRecords = appointmentService.getMyMedicalRecords(principal.getUsername());

        model.addAttribute("myMedicalRecords", myMedicalRecords);
        return "contents/member/medical-records";
    }

    @GetMapping("/records/{appointmentSeq}")
    public String medicalRecordsDetails(
            Model model,
            @PathVariable Long appointmentSeq
    ) {
        ResponseDetailMedicalHistoryDto myMedicalRecordDetail = appointmentService.getMyMedicalRecordDetail(appointmentSeq);
        model.addAttribute("myMedicalRecordDetail", myMedicalRecordDetail);
        return "contents/member/medical-records-details";
    }


    @GetMapping("/profile")
    public String myPage(@AuthenticationPrincipal MemberUserDetails principal, Model model) {
        MemberInfoResponseDto memberInfoResponseDto = memberService.getMemberByEmail(principal.getUsername());
        model.addAttribute("member", memberInfoResponseDto);
        return "contents/member/my-page";
    }

    @GetMapping("/profile/form")
    public String updateProfile(@AuthenticationPrincipal UserDetails principal, Model model) {
        MemberInfoResponseDto memberInfoResponseDto = memberService.getMemberByEmail(principal.getUsername());
        model.addAttribute("member", memberInfoResponseDto);
        return "contents/member/my-page-form";
    }

    @GetMapping("/reviews")
    public String myReviewList(
            @AuthenticationPrincipal UserDetails principal,
            Model model
    ) {
        List<ResponseReviewDto> responseReviewDtos = reviewService.findReviewsByMemberEmail(principal.getUsername());
        model.addAttribute("responseReviewDtos", responseReviewDtos);
        return "contents/member/my-review";
    }

    @GetMapping("/appointments")
    public String getMyReservationList(
            @AuthenticationPrincipal UserDetails principal,
            Model model
    ) {
        List<ResponseAppointmentDto> responseAppointmentDtos = appointmentService.getAppointmentListByMemberEmail(principal.getUsername());
        model.addAttribute("responseAppointmentDtos", responseAppointmentDtos);
        return "contents/member/my-reservations";
    }

    @GetMapping("/hospitals")
    public String findFavorites(
            Model model,
            @AuthenticationPrincipal UserDetails principal
    ) { List<ResponseFavoritesHospitalDto> responseFavoritesHospitalDtos = memberService.getFavoritesByMemberEmail(principal.getUsername());
        model.addAttribute("favoritesHospitalDtos", responseFavoritesHospitalDtos);
        return "contents/member/regular-hospital";
    }

    @GetMapping("/children/list")
    public String getAllChildren(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("사용자 정보를 찾을 수 없습니다."));
        List<ChildrenDto> childrenList = childrenService.getAllChildrenByMemberSeq(member.getMemberSeq());
        model.addAttribute("childrenList", childrenList);
        return "contents/member/my-children";
    }

    @GetMapping("/children/form")
    public String addChildrenForm(Model model) {
        model.addAttribute("childrenRequestDto", new ChildrenRequestDto());
        return "contents/member/my-children-add";
    }

    @PostMapping("/children/form")
    public String createChild(
            @Valid ChildrenRequestDto childrenRequestDto,
            BindingResult bindingResult,
            MultipartFile profileImage,
            @AuthenticationPrincipal UserDetails member
    ) {
        if (bindingResult.hasErrors()) {
            return "contents/member/my-children-add";
        }
        ResultFileStore resultFileStore;
        resultFileStore = fileStore.storeFile(profileImage);
        String email = member.getUsername();
        childrenService.createChildren(childrenRequestDto, resultFileStore, email);

        return "redirect:/my/children/list";
    }

    @PostMapping("/children/{id}")
    public String deleteChild(@PathVariable Long id) {
        childrenService.deleteChild(id);
        return "redirect:/my/children/list"; // 자녀 목록 페이지로 리디렉션
    }

    @GetMapping("/records/reviews/{review_seq}")
    public String removeReview(@PathVariable("review_seq") long reviewSeq) {
        reviewService.removeReview(reviewSeq);
        return "redirect:/my/reviews";
    }

}
