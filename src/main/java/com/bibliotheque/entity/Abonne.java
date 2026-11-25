package com.bibliotheque.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de base des abonnés de la bibliothèque.
 * Stratégie SINGLE_TABLE : Etudiant, Enseignant et Groupe
 * partagent la table abonne, distingués par type_abonne.
 */
@Entity
@Table(name = "abonne")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_abonne", discriminatorType = DiscriminatorType.STRING)
public abstract class Abonne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    // Côté inverse de la relation ; la FK est portée par Emprunt
    @OneToMany(mappedBy = "abonne", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Emprunt> emprunts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<Emprunt> getEmprunts() {
        return emprunts;
    }

    public void setEmprunts(List<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }
}
