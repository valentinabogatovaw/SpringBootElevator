package com.elevator.controller;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WEBController {

    @RequestMapping("/hi")
    public String home() {
        return "hi";
    }

}
