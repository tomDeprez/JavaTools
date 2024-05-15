package com.voiture;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.entity.Image;
import com.entity.UserCar;
import com.entity.Voiture;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.repository")
@ComponentScan(basePackages = "com")
@EntityScan(basePackages = "com.entity")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Controller
    public class MainController {

        @GetMapping("/")
        public String index(Model model) {

            List<UserCar> users = new ArrayList<>();

            List<Voiture> listDeVoitures = new ArrayList<>();
            UserCar leo = new UserCar("Salomer", "Leo");
            users.add(leo);
            listDeVoitures
                    .add(new Voiture(220.5f, "Tesla Model S", "Rouge", new Image("/images/tesla-mode-s.jpg", null), users));
            listDeVoitures.add(new Voiture(125.3f, "Renault Zoe", "Bleu", null, users));

            model.addAttribute("message", "Bienvenue sur la page d'accueil !");
            model.addAttribute("listDeVoitures", listDeVoitures);
            return "index";
        }

        @GetMapping("/test")
        public String test(Model model) {
            return "test";
        }
    }
}
