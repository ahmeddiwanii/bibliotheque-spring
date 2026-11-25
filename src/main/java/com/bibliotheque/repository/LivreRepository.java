package com.bibliotheque.repository;

import com.bibliotheque.entity.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivreRepository extends JpaRepository<Livre, Long> {
    List<Livre> findByTitreContainingIgnoreCase(String titre);
    List<Livre> findByAuteurContainingIgnoreCase(String auteur);
    List<Livre> findByDisponibilite(boolean disponibilite);
    List<Livre> findByCategorieId(Long categorieId);
    List<Livre> findByCategorieIdAndDisponibilite(Long categorieId, boolean disponibilite);
    List<Livre> findByTitreContainingIgnoreCaseOrAuteurContainingIgnoreCase(String titre, String auteur);
}
