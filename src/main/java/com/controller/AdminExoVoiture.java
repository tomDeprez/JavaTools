package com.controller;

import java.beans.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Voiture;

@Controller
@RequestMapping("/admin/exo/voiture")
public class AdminExoVoiture {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/afficher")
    public String afficherLesVoitures(Model model) {

        return "admin/exoVoiture/index";
    }

}
