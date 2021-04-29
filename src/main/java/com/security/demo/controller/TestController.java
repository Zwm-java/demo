package com.security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin")
public class TestController {
    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/")
    public String list() {
        return "home";
    }
    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }
    @GetMapping("/vip")
    public String vip() {
        return "VIP";
    }
    @GetMapping("/root")
    public String root() {
        return "ROOT";
    }

}