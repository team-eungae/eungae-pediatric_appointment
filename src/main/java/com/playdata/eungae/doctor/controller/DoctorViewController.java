package com.playdata.eungae.doctor.controller;

import com.playdata.eungae.doctor.dto.DoctorRegisterRequestDto;
import com.playdata.eungae.doctor.dto.DoctorResponseDto;
import com.playdata.eungae.doctor.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/hospital")
@Controller
public class DoctorViewController {
    private final DoctorService doctorService;

    @GetMapping("/doctors/form")
    public String getHospitalDoctorForm() {
        return "contents/hospital/create-doctor";
    }

    @PostMapping("/doctors")
    @ResponseStatus(HttpStatus.CREATED)
    public String createHospitalDoctor(@ModelAttribute DoctorRegisterRequestDto doctorRegisterRequestDto) {
        //@AuthenticationPrincipal 적용 후 사용 예정
        //String hospitalId = principal.getUsername();
        //doctorService.createDoctor(doctorRegisterRequestDto, hospitalId);
        doctorService.createDoctor(doctorRegisterRequestDto, "hospitalIdTest");

        return "redirect:/hospital/doctors/form";
    }

    @PatchMapping("/doctors")
    @ResponseStatus(HttpStatus.OK)
    public String deleteHospitalDoctor(@PathVariable Long doctorSeq) {
        doctorService.deleteDoctor(doctorSeq);

        return "redirect:/hospital/doctors";
    }

    @GetMapping("/doctors")
    public String getDoctorsByHospital(Model model) {
        //@AuthenticationPrincipal 적용 후 사용 예정
//        List<DoctorResponseDto> doctorList = doctorService.findDoctorsByHospitalSeq(hospitalSeq);
//        model.addAttribute("doctorList", doctorList);
        return "contents/hospital/doctor-list";
    }

    @GetMapping("/doctors")
    public String getDoctor(@PathVariable Long doctorSeq, Model model) {
        DoctorResponseDto doctor = doctorService.getDoctor(doctorSeq);

        model.addAttribute("doctor", doctor);
        return "contents/hospital/doctor-detail";
    }

}
