package com.bibliotheque.repository;

import com.bibliotheque.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findByNameIgnoreCase(String name);
    List<Categorie> findByNameContainingIgnoreCase(String name);
}
