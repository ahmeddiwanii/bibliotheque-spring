package com.bibliotheque.repository;

import com.bibliotheque.entity.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** Accès aux emprunts et requêtes de disponibilité / retard. */
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    List<Emprunt> findByAbonneId(Long abonneId);
    List<Emprunt> findByLivreId(Long livreId);
    List<Emprunt> findByLivreIdOrderByDateEmpruntDesc(Long livreId);
    List<Emprunt> findByDateEmpruntBetween(LocalDate debut, LocalDate fin);
    List<Emprunt> findByDateRetourEffectiveIsNull();

    /** Emprunt en cours le plus récent pour un livre donné. */
    Optional<Emprunt> findFirstByLivreIdAndDateRetourEffectiveIsNullOrderByDateEmpruntDesc(Long livreId);

    @Query("SELECT e FROM Emprunt e WHERE e.dateRetourEffective IS NULL AND e.dateRetourPrevue < :aujourdHui")
    List<Emprunt> findEmpruntsEnRetard(@Param("aujourdHui") LocalDate aujourdHui);

    /** Identifiants des livres empruntés sur une période (chevauchement). */
    @Query("""
            SELECT DISTINCT e.livre.id FROM Emprunt e
            WHERE e.dateEmprunt <= :fin
              AND (e.dateRetourEffective IS NULL OR e.dateRetourEffective >= :debut)
            """)
    List<Long> findLivreIdsEmpruntesPendant(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}
