package com.controller;

import java.beans.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Voiture;
import com.repository.VoitureRepository;

@Controller
@RequestMapping("/admin/exo/voiture")
public class AdminExoVoiture {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private VoitureRepository voitureRepository;

    @GetMapping("/afficher")
    public String afficherLesVoitures(Model model) {
        List<Voiture> voitures = voitureRepository.findAll();

        model.addAttribute("voitures", voitures);
        return "admin/exoVoiture/index";
    }

    @GetMapping("/supprimer")
    public String supprimerUneVoiture(@RequestParam Long id) {
        voitureRepository.deleteById(id);
        return "redirect:/admin/exo/voiture/afficher";
    }

    @GetMapping("/ajouter/modifier/afficher")
    public String ajouterUneVoiture(Model model, @RequestParam(defaultValue = "0") Long id) {


        return "admin/exoVoiture/form";
    }

    @GetMapping("/ajouter/modifier")
    public String ajouterUneVoiture(@RequestParam(defaultValue = "0") Long id) {
        
        return "admin/exoVoiture/form";
    }
}
