package com.voiture;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jni.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.entity.Image;
import com.entity.UserCar;
import com.entity.Voiture;

import org.springframework.ui.Model;

@SpringBootApplication
@Controller
public class Main extends AdminVoiture {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/")
    public String index(Model model) {

        List<UserCar> users = new ArrayList<>();

        List<Voiture> listDeVoitures = new ArrayList<>();
        UserCar leo = new UserCar("Salomer", "Leo");
        users.add(leo);
        listDeVoitures
                .add(new Voiture(220.5f, "Tesla Model S", "Rouge", new Image("/images/tesla-mode-s.jpg", null), users));
        listDeVoitures.add(new Voiture(125.3f, "Renault Zoe", "Bleu", null, users));

        listDeVoitures.get(0).addUser(new UserCar("DEPREZ", "Tom"));
        listDeVoitures.get(1).addUser(new UserCar("DEPREZ", "Tom"));
        for (UserCar user : listDeVoitures.get(0).getUser()) {
            System.out.println(user.getNom());
        }
        model.addAttribute("message", "Bienvenue sur la page d'accueil !");
        model.addAttribute("listDeVoitures", listDeVoitures);
        return "index";
    }

    @GetMapping("/test")
    public String test(Model model) {

        return "test";
    }

}