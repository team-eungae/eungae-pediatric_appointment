package com.playdata.eungae;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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
    public String map(){
        return "contents/hospital/map";
    }




}
