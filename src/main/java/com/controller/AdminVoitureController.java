package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Connection;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.config.DatabaseConfig;
import com.entity.Image;
import com.entity.Voiture;
import com.repository.ImageRepository;
import com.repository.VoitureRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

@Controller
@RequestMapping("/admin/voiture")
public class AdminVoitureController {

    @Autowired
    private VoitureRepository voitureRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private DataSource dataSource;

    @GetMapping("/afficher")
    public String afficherVoitures(Model model) {
        List<Voiture> listDeVoitures = voitureRepository.findAll();
        model.addAttribute("listDeVoitures", listDeVoitures);
        return "admin/voiture/index";
    }

    @PostMapping("/ajouter")
    public String ajouterVoiture(@ModelAttribute Voiture voiture) {
        if (voiture.getImage() != null && voiture.getImage().getId() == null) {
            Image image = voiture.getImage();
            imageRepository.save(image); // Assurez-vous que imageRepository est correctement injecté
            voiture.setImage(image);
        }
        for (int i = 0; i < 10; i++) {
            Voiture voiture2 = new Voiture();
            voiture2 = voiture;
            voiture2.setId(Long.parseLong((i + 100)));
            voitureRepository.save(voiture2); 
        }
        return "redirect:/admin/voiture/afficher";
    }

    @GetMapping("/supprimer")
    public String supprimerVoiture(@RequestParam Long id) {
        Optional<Voiture> voiture = voitureRepository.findById(id);
        voiture.ifPresent(voitureRepository::delete);
        return "redirect:/admin/voiture/afficher";
    }

    
    @GetMapping("/ajouter/form")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("voiture", new Voiture());
        return "admin/voiture/form";
    }

    @GetMapping("/afficher2")
    public String afficherVoitures2(Model model) {
        List<Voiture> listDeVoitures = executerRequeteSql();
        model.addAttribute("listDeVoitures", listDeVoitures);
        return "admin/voiture/index";
    }

    private List<Voiture> executerRequeteSql() {
        String sql = "SELECT v.id, v.vitesse, v.modele, v.couleur, i.id AS image_id, i.url, i.alt_text " +
                "FROM voiture v " +
                "LEFT JOIN image i ON v.image_id = i.id";

        List<Voiture> voitures = new ArrayList<>();

        try (java.sql.Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            System.out.println("Connexion à la base de données établie et requête exécutée.");

            while (rs.next()) {
                System.out.println("Enregistrement trouvé dans la base de données.");
                Voiture voiture = new Voiture();
                voiture.setId(rs.getLong("id"));
                voiture.setVitesse(rs.getFloat("vitesse"));
                voiture.setModele(rs.getString("modele"));
                voiture.setCouleur(rs.getString("couleur"));

                Image image = new Image();
                image.setId(rs.getLong("image_id"));
                image.setUrl(rs.getString("url"));
                image.setAltText(rs.getString("alt_text"));

                voiture.setImage(image);
                voitures.add(voiture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Nombre de voitures récupérées : " + voitures.size());
        return voitures;
    }

}
