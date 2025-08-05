package com.ama.don.interior.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("interior/home")
    public String interiorHome() {
        return "interior/home";
    }
}
