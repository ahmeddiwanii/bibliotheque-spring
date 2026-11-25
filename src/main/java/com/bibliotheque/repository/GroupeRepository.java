package com.bibliotheque.repository;

import com.bibliotheque.entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupeRepository extends JpaRepository<Groupe, Long> {
    List<Groupe> findByNomGroupeContainingIgnoreCase(String nomGroupe);
}
