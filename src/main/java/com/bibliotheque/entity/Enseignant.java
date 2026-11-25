package com.bibliotheque.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/** Abonné de type enseignant (colonne discriminante ENSEIGNANT). */
@Entity
@DiscriminatorValue("ENSEIGNANT")
public class Enseignant extends Abonne {

    private String specialite;

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
