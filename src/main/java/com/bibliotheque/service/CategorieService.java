package com.bibliotheque.service;

import com.bibliotheque.entity.Categorie;
import com.bibliotheque.repository.CategorieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Gestion des catégories du catalogue. */
@Service
@Transactional
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    public Categorie findById(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categorie not found: " + id));
    }

    public List<Categorie> searchByName(String name) {
        return categorieRepository.findByNameContainingIgnoreCase(name);
    }

    public Categorie findByName(String name) {
        return categorieRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Categorie not found: " + name));
    }

    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie update(Long id, Categorie data) {
        Categorie existing = findById(id);
        existing.setName(data.getName());
        return categorieRepository.save(existing);
    }

    public void delete(Long id) {
        if (!categorieRepository.existsById(id)) {
            throw new RuntimeException("Categorie not found: " + id);
        }
        categorieRepository.deleteById(id);
    }
}
