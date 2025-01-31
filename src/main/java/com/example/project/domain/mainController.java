package com.example.project.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class mainController {
    @GetMapping("/home")
    public String welcome(){
        return "hello";
    }
}
