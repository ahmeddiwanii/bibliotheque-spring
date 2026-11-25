package com.bibliotheque.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/** Abonné de type étudiant (colonne discriminante ETUDIANT). */
@Entity
@DiscriminatorValue("ETUDIANT")
public class Etudiant extends Abonne {

    private String niveau;

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
