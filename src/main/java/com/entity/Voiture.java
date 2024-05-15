package com.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float vitesse;
    private String modele;
    private String couleur;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToMany
    @JoinTable(
        name = "VoitureUserCar",
        joinColumns = @JoinColumn(name = "voiture_id"),
        inverseJoinColumns = @JoinColumn(name = "user_car_id")
    )
    private List<UserCar> userCar;

    public Voiture() {
    }

    public Voiture(float vitesse, String modele, String couleur, Image image, List<UserCar> userCar) {
        this.vitesse = vitesse;
        this.modele = modele;
        this.couleur = couleur;
        this.image = image;
        this.userCar = userCar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<UserCar> getUserCar() {
        return userCar;
    }

    public void setUserCar(List<UserCar> userCar) {
        this.userCar = userCar;
    }
}
