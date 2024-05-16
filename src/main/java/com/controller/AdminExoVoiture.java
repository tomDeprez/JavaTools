package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Voiture;

@Controller
@RequestMapping("/admin/exo/voiture")
public class AdminExoVoiture {

    @GetMapping("/afficher")
    public String afficherLesVoitures(Model model) {
        return "admin/exoVoiture/index";
    }

}
