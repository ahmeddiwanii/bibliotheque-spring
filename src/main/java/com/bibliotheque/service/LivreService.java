package com.bibliotheque.service;

import com.bibliotheque.dto.HistoriqueEmpruntDto;
import com.bibliotheque.dto.LivreDisponibiliteDto;
import com.bibliotheque.entity.Categorie;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.repository.CategorieRepository;
import com.bibliotheque.repository.LivreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Catalogue des livres : CRUD, filtres et délégation
 * vers les opérations d'emprunt / disponibilité.
 */
@Service
@Transactional
public class LivreService {

    private final LivreRepository livreRepository;
    private final CategorieRepository categorieRepository;
    private final EmpruntService empruntService;

    public LivreService(LivreRepository livreRepository,
                        CategorieRepository categorieRepository,
                        EmpruntService empruntService) {
        this.livreRepository = livreRepository;
        this.categorieRepository = categorieRepository;
        this.empruntService = empruntService;
    }

    public List<Livre> findAll() {
        return livreRepository.findAll();
    }

    public Livre findById(Long id) {
        return livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre not found: " + id));
    }

    public List<Livre> search(String q) {
        return livreRepository.findByTitreContainingIgnoreCaseOrAuteurContainingIgnoreCase(q, q);
    }

    public List<LivreDisponibiliteDto> searchAvecDisponibilite(String q) {
        return empruntService.searchAvecDisponibilite(q);
    }

    public LivreDisponibiliteDto disponibilite(Long id) {
        return empruntService.disponibiliteLivre(id);
    }

    public List<HistoriqueEmpruntDto> historique(Long id) {
        return empruntService.historiqueLivre(id);
    }

    public List<Livre> disponiblesPendant(LocalDate debut, LocalDate fin) {
        return empruntService.findLivresDisponiblesPendant(debut, fin);
    }

    public List<Livre> findByTitre(String titre) {
        return livreRepository.findByTitreContainingIgnoreCase(titre);
    }

    public List<Livre> findByAuteur(String auteur) {
        return livreRepository.findByAuteurContainingIgnoreCase(auteur);
    }

    public List<Livre> findByDisponibilite(boolean disponibilite) {
        return livreRepository.findByDisponibilite(disponibilite);
    }

    public List<Livre> findByCategorie(Long categorieId) {
        return livreRepository.findByCategorieId(categorieId);
    }

    public List<Livre> findByCategorieAndDisponibilite(Long categorieId, boolean disponibilite) {
        return livreRepository.findByCategorieIdAndDisponibilite(categorieId, disponibilite);
    }

    public Livre save(Livre livre) {
        if (livre.getCategorie() != null && livre.getCategorie().getId() != null) {
            Categorie categorie = categorieRepository.findById(livre.getCategorie().getId())
                    .orElseThrow(() -> new RuntimeException("Categorie not found"));
            livre.setCategorie(categorie);
        }
        return livreRepository.save(livre);
    }

    public Livre update(Long id, Livre data) {
        Livre existing = findById(id);
        existing.setTitre(data.getTitre());
        existing.setAuteur(data.getAuteur());
        existing.setDisponibilite(data.isDisponibilite());
        if (data.getCategorie() != null && data.getCategorie().getId() != null) {
            Categorie categorie = categorieRepository.findById(data.getCategorie().getId())
                    .orElseThrow(() -> new RuntimeException("Categorie not found"));
            existing.setCategorie(categorie);
        }
        return livreRepository.save(existing);
    }

    public void delete(Long id) {
        if (!livreRepository.existsById(id)) {
            throw new RuntimeException("Livre not found: " + id);
        }
        livreRepository.deleteById(id);
    }
}
