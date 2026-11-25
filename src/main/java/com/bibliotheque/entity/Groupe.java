package com.bibliotheque.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/** Abonné de type groupe (colonne discriminante GROUPE). */
@Entity
@DiscriminatorValue("GROUPE")
public class Groupe extends Abonne {

    private String nomGroupe;

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }
}
