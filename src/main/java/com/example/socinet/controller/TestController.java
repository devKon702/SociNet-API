package com.example.socinet.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/test")
public class TestController {
    @GetMapping("")
    public String test(HttpServletRequest request){
        return request.getHeader("X-FORWARDED-FOR");
    }
}
