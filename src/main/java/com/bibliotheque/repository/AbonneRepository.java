package com.bibliotheque.repository;

import com.bibliotheque.entity.Abonne;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AbonneRepository extends JpaRepository<Abonne, Long> {
    List<Abonne> findByNomContainingIgnoreCase(String nom);
    List<Abonne> findByPrenomContainingIgnoreCase(String prenom);
    List<Abonne> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}
