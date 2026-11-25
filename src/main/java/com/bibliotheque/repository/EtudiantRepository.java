package com.bibliotheque.repository;

import com.bibliotheque.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    List<Etudiant> findByNiveauIgnoreCase(String niveau);
    List<Etudiant> findByNiveauContainingIgnoreCase(String niveau);
    List<Etudiant> findByNomContainingIgnoreCase(String nom);
}
