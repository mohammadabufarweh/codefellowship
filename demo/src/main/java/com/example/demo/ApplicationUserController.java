package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Column;
import java.security.Principal;


@Controller
public class ApplicationUserController {

    @GetMapping("/")
    @ResponseBody
    public String getHome(){
//        m.addAttribute("userName", p.getName());
        return "Hello World ";
    }
}
