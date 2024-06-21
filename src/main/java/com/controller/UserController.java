package com.controller;

import com.entity.User;
import com.service.CustomUserDetails;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/auth")
    public String showAuthForm(Model model, @ModelAttribute("loginError") String loginError,
            @ModelAttribute("logout") String logout) {
        model.addAttribute("user", new User());
        if (loginError != null) {
            model.addAttribute("error", "Email ou mot de passe incorrect");
        }
        if (logout != null) {
            model.addAttribute("message", "Vous avez été déconnecté avec succès");
        }
        return "auth/index";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authMode", "register");
            return "auth/index";
        }
        userService.saveUser(user);
        return "redirect:/auth?login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
        User authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
        if (authenticatedUser == null) {
            redirectAttributes.addAttribute("loginError", true);
            return "redirect:/auth?login";
        }
    
        UserDetails userDetails = new CustomUserDetails(authenticatedUser, Collections.singletonList(
                new SimpleGrantedAuthority(authenticatedUser.getRole())));
    
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    
        // Log pour vérifier l'authentification
        System.out.println("Authentication set: " + SecurityContextHolder.getContext().getAuthentication());
    
        return "redirect:/user";
    }
    
    

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User loggedInUser = userService.findByEmail(userDetails.getUsername());
            model.addAttribute("loggedInUser", loggedInUser);
        }
        return "user/index";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/auth?logout";
    }
}
