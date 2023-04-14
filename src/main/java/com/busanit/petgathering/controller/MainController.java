package com.busanit.petgathering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    // 메인 페이지로 이동
    @GetMapping(value = "/")
    public String main(){
    return "main";
    }

}