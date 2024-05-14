package com.voiture;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Image;
import com.entity.UserCar;
import com.entity.Voiture;

public class AdminVoiture {
    
    @GetMapping("/afficher/voitures")
    public String afficherVoitures(Model model) {
        List<UserCar> users = new ArrayList<>();

        List<Voiture> listDeVoitures = new ArrayList<>();
        UserCar leo = new UserCar("Salomer", "Leo");
        users.add(leo);
        listDeVoitures.add(new Voiture(220.5f, "Tesla Model S", "Rouge", new Image("/images/tesla-mode-s.jpg", null), users));
        listDeVoitures.add(new Voiture(125.3f, "Renault Zoe", "Bleu", new Image("/images/renault-zoe-nouvelle-removebg-preview.png", null), users));
        model.addAttribute("listDeVoitures", listDeVoitures);
        return "admin/voiture/index";
    }

    @GetMapping("/supprimer/voiture")
    public String supprimerVoiture(Model model, @RequestParam String id) {

        return "test";
    }

    @GetMapping("/ajouter/voiture")
    public String ajouterVoiture(Model model){

        return "test";
    }
}
