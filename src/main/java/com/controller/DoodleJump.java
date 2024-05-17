package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.entity.Image;
import com.entity.Voiture;

@Controller
public class DoodleJump {

    @GetMapping("/DoodleJump")
    public String doodleJump() {
        return "DoodleJump/index";
    }

    
}
