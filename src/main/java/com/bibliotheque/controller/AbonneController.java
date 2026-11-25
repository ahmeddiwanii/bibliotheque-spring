package com.bibliotheque.controller;

import com.bibliotheque.entity.*;
import com.bibliotheque.service.AbonneService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST des abonnés (étudiants, enseignants, groupes).
 */
@RestController
@RequestMapping("/api/abonnes")
public class AbonneController {

    private final AbonneService abonneService;

    public AbonneController(AbonneService abonneService) {
        this.abonneService = abonneService;
    }

    @GetMapping
    public List<Abonne> getAll() {
        return abonneService.findAll();
    }

    @GetMapping("/search")
    public List<Abonne> search(@RequestParam String q) {
        return abonneService.search(q);
    }

    @GetMapping("/nom")
    public List<Abonne> findByNom(@RequestParam String nom) {
        return abonneService.findByNom(nom);
    }

    @GetMapping("/enseignants")
    public List<Enseignant> getEnseignants() {
        return abonneService.findAllEnseignants();
    }

    @GetMapping("/enseignants/specialite")
    public List<Enseignant> getEnseignantsBySpecialite(@RequestParam String q) {
        return abonneService.findEnseignantsBySpecialite(q);
    }

    @PostMapping("/enseignants")
    @ResponseStatus(HttpStatus.CREATED)
    public Enseignant createEnseignant(@RequestBody Enseignant enseignant) {
        return abonneService.saveEnseignant(enseignant);
    }

    @PutMapping("/enseignants/{id}")
    public Enseignant updateEnseignant(@PathVariable Long id, @RequestBody Enseignant enseignant) {
        return abonneService.updateEnseignant(id, enseignant);
    }

    @GetMapping("/etudiants")
    public List<Etudiant> getEtudiants() {
        return abonneService.findAllEtudiants();
    }

    @GetMapping("/etudiants/niveau")
    public List<Etudiant> getEtudiantsByNiveau(@RequestParam String niveau) {
        return abonneService.findEtudiantsByNiveau(niveau);
    }

    @PostMapping("/etudiants")
    @ResponseStatus(HttpStatus.CREATED)
    public Etudiant createEtudiant(@RequestBody Etudiant etudiant) {
        return abonneService.saveEtudiant(etudiant);
    }

    @PutMapping("/etudiants/{id}")
    public Etudiant updateEtudiant(@PathVariable Long id, @RequestBody Etudiant etudiant) {
        return abonneService.updateEtudiant(id, etudiant);
    }

    @GetMapping("/groupes")
    public List<Groupe> getGroupes() {
        return abonneService.findAllGroupes();
    }

    @GetMapping("/groupes/search")
    public List<Groupe> getGroupesByNom(@RequestParam String nomGroupe) {
        return abonneService.findGroupesByNom(nomGroupe);
    }

    @PostMapping("/groupes")
    @ResponseStatus(HttpStatus.CREATED)
    public Groupe createGroupe(@RequestBody Groupe groupe) {
        return abonneService.saveGroupe(groupe);
    }

    @PutMapping("/groupes/{id}")
    public Groupe updateGroupe(@PathVariable Long id, @RequestBody Groupe groupe) {
        return abonneService.updateGroupe(id, groupe);
    }

    @GetMapping("/{id}")
    public Abonne getById(@PathVariable Long id) {
        return abonneService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        abonneService.delete(id);
    }
}
