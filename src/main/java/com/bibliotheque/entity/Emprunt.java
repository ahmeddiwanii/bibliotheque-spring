package com.bibliotheque.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Enregistrement d'un emprunt.
 * dateRetourPrevue : échéance prévue.
 * dateRetourEffective : null tant que le livre n'est pas rendu.
 */
@Entity
@Table(name = "emprunt")
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;

    @ManyToOne
    @JoinColumn(name = "abonne_id")
    private Abonne abonne;

    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    /** Alias conservé pour les clients qui envoient encore dateRetour. */
    public LocalDate getDateRetour() {
        return dateRetourPrevue;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetourPrevue = dateRetour;
    }

    public boolean isEnCours() {
        return dateRetourEffective == null;
    }

    public Abonne getAbonne() {
        return abonne;
    }

    public void setAbonne(Abonne abonne) {
        this.abonne = abonne;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }
}
