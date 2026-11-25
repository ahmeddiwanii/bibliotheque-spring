package com.bibliotheque.dto;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.Livre;

import java.time.LocalDate;

/** Réponse enrichie : disponibilité d'un livre et délai de retour éventuel. */
public class LivreDisponibiliteDto {

    private Livre livre;
    private boolean disponible;
    private String message;
    private LocalDate disponibleLe;
    private Long joursRestants;
    private LocalDate dateEmprunt;
    private Abonne empruntePar;

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDisponibleLe() {
        return disponibleLe;
    }

    public void setDisponibleLe(LocalDate disponibleLe) {
        this.disponibleLe = disponibleLe;
    }

    public Long getJoursRestants() {
        return joursRestants;
    }

    public void setJoursRestants(Long joursRestants) {
        this.joursRestants = joursRestants;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public Abonne getEmpruntePar() {
        return empruntePar;
    }

    public void setEmpruntePar(Abonne empruntePar) {
        this.empruntePar = empruntePar;
    }
}
