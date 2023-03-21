package com.spring_security.user_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
@Slf4j
@RequiredArgsConstructor
public class GreetingController {

    @GetMapping
    public String greeting() {
        log.info("Greeting page called");
        return "greeting";
    }
}
