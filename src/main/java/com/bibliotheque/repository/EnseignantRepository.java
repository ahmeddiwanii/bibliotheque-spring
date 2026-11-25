package com.bibliotheque.repository;

import com.bibliotheque.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    List<Enseignant> findBySpecialiteContainingIgnoreCase(String specialite);
    List<Enseignant> findByNomContainingIgnoreCase(String nom);
}
