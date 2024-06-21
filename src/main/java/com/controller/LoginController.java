package com.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/test/loginPage")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/test/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, 
                        HttpServletResponse response, HttpServletRequest request) {
        // Pour des raisons de sécurité, vous devriez vérifier le nom d'utilisateur et
        // le mot de passe dans une base de données
        // Ici, nous allons simplement simuler une connexion réussie pour tout
        // utilisateur
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            // Simuler une connexion réussie en créant un cookie
            Cookie userCookie = new Cookie("user", "HJDBZQHDbQJZDBZQKBDKZBDLBQDZLIdBQUDb");
            userCookie.setMaxAge(60 * 60 * 24); // 1 jour
            response.addCookie(userCookie);
            request.setAttribute("message", "Connexion réussie ! Bienvenue, " + username + ".");
        } else {
            request.setAttribute("message", "Nom d'utilisateur ou mot de passe incorrect.");
        }
        return "loginResult";
    }
}
