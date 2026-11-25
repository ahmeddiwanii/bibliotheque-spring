package com.bibliotheque.controller;

import com.bibliotheque.dto.HistoriqueEmpruntDto;
import com.bibliotheque.dto.LivreDisponibiliteDto;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.service.LivreService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Endpoints REST du catalogue (recherche, disponibilité, historique).
 */
@RestController
@RequestMapping("/api/livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping
    public List<Livre> getAll(
            @RequestParam(required = false) Boolean disponibilite,
            @RequestParam(required = false) Long categorieId) {
        if (categorieId != null && disponibilite != null) {
            return livreService.findByCategorieAndDisponibilite(categorieId, disponibilite);
        }
        if (categorieId != null) {
            return livreService.findByCategorie(categorieId);
        }
        if (disponibilite != null) {
            return livreService.findByDisponibilite(disponibilite);
        }
        return livreService.findAll();
    }

    @GetMapping("/search")
    public List<Livre> search(@RequestParam String q) {
        return livreService.search(q);
    }

    /** Recherche avec indication du délai de disponibilité. */
    @GetMapping("/search-dispo")
    public List<LivreDisponibiliteDto> searchDispo(@RequestParam String q) {
        return livreService.searchAvecDisponibilite(q);
    }

    /** Livres sans emprunt chevauchant la période donnée. */
    @GetMapping("/disponibles-periode")
    public List<Livre> disponiblesPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return livreService.disponiblesPendant(debut, fin);
    }

    @GetMapping("/titre")
    public List<Livre> findByTitre(@RequestParam String titre) {
        return livreService.findByTitre(titre);
    }

    @GetMapping("/auteur")
    public List<Livre> findByAuteur(@RequestParam String auteur) {
        return livreService.findByAuteur(auteur);
    }

    @GetMapping("/disponibles")
    public List<Livre> getDisponibles() {
        return livreService.findByDisponibilite(true);
    }

    @GetMapping("/indisponibles")
    public List<Livre> getIndisponibles() {
        return livreService.findByDisponibilite(false);
    }

    @GetMapping("/categorie/{categorieId}")
    public List<Livre> getByCategorie(@PathVariable Long categorieId) {
        return livreService.findByCategorie(categorieId);
    }

    @GetMapping("/{id}/historique")
    public List<HistoriqueEmpruntDto> historique(@PathVariable Long id) {
        return livreService.historique(id);
    }

    @GetMapping("/{id}/disponibilite")
    public LivreDisponibiliteDto disponibilite(@PathVariable Long id) {
        return livreService.disponibilite(id);
    }

    @GetMapping("/{id}")
    public Livre getById(@PathVariable Long id) {
        return livreService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livre create(@RequestBody Livre livre) {
        return livreService.save(livre);
    }

    @PutMapping("/{id}")
    public Livre update(@PathVariable Long id, @RequestBody Livre livre) {
        return livreService.update(id, livre);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        livreService.delete(id);
    }
}
