package com.playdata.eungae;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signUp(){
        return "contents/member/sign-up";
    }

    @GetMapping("/map")
    public String findHospital(){
        return "contents/hospital/find-hospital";
    }

    @GetMapping("/map/search")
    public String searchHospital(String keyword){
        log.info(keyword);
        return "contents/hospital/search-hospital";
    }



}
