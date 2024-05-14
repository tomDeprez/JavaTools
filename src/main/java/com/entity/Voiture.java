package com.entity;

import java.util.ArrayList;
import java.util.List;

public class Voiture {
    private float vitesse;
    private String modele;
    private String couleur;
    private Image image;
    private List<UserCar> userCar;

    public Voiture(float vitesse, String modele, String couleur, Image image, List<UserCar> userCar) {
        this.vitesse = vitesse;
        this.modele = modele;
        this.couleur = couleur;
        this.image = image;
        this.userCar = userCar;
    }

    public float getVitesse() {
        return vitesse;
    }

    public String getModele() {
        return modele;
    }

    public String getCouleur() {
        return couleur;
    }

    public Image getImage() {
        return image;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void addUser(UserCar user) {
        this.userCar.add(user);
    }

    public List<UserCar> getUser() {
        return userCar;
    }

    public void setUser(List<UserCar> user) {
        this.userCar = user;
    }
    
}
