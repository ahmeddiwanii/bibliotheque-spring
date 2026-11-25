package com.bibliotheque.service;

import com.bibliotheque.dto.HistoriqueEmpruntDto;
import com.bibliotheque.dto.LivreDisponibiliteDto;
import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.Emprunt;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.repository.AbonneRepository;
import com.bibliotheque.repository.EmpruntRepository;
import com.bibliotheque.repository.LivreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Gestion des emprunts : création, retour, retards,
 * historique et calcul de disponibilité d'un livre.
 */
@Service
@Transactional
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final AbonneRepository abonneRepository;
    private final LivreRepository livreRepository;

    public EmpruntService(EmpruntRepository empruntRepository,
                          AbonneRepository abonneRepository,
                          LivreRepository livreRepository) {
        this.empruntRepository = empruntRepository;
        this.abonneRepository = abonneRepository;
        this.livreRepository = livreRepository;
    }

    public List<Emprunt> findAll() {
        return empruntRepository.findAll();
    }

    public Emprunt findById(Long id) {
        return empruntRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprunt not found: " + id));
    }

    public Emprunt emprunter(Long abonneId, Long livreId, LocalDate dateRetourPrevue) {
        Abonne abonne = abonneRepository.findById(abonneId)
                .orElseThrow(() -> new RuntimeException("Abonne not found: " + abonneId));
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre not found: " + livreId));

        if (!livre.isDisponibilite()) {
            LivreDisponibiliteDto info = buildDisponibilite(livre);
            throw new RuntimeException(info.getMessage());
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setAbonne(abonne);
        emprunt.setLivre(livre);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(
                dateRetourPrevue != null ? dateRetourPrevue : LocalDate.now().plusDays(15));
        emprunt.setDateRetourEffective(null);

        livre.setDisponibilite(false);
        return empruntRepository.save(emprunt);
    }

    public Emprunt retourner(Long empruntId) {
        Emprunt emprunt = findById(empruntId);
        if (emprunt.getDateRetourEffective() != null) {
            throw new RuntimeException("Ce livre est deja retourne");
        }
        emprunt.setDateRetourEffective(LocalDate.now());
        emprunt.getLivre().setDisponibilite(true);
        return empruntRepository.save(emprunt);
    }

    public List<Emprunt> findByAbonne(Long abonneId) {
        return empruntRepository.findByAbonneId(abonneId);
    }

    public List<Emprunt> findByLivre(Long livreId) {
        return empruntRepository.findByLivreId(livreId);
    }

    public List<HistoriqueEmpruntDto> historiqueLivre(Long livreId) {
        if (!livreRepository.existsById(livreId)) {
            throw new RuntimeException("Livre not found: " + livreId);
        }
        List<HistoriqueEmpruntDto> result = new ArrayList<>();
        for (Emprunt e : empruntRepository.findByLivreIdOrderByDateEmpruntDesc(livreId)) {
            HistoriqueEmpruntDto dto = new HistoriqueEmpruntDto();
            dto.setEmpruntId(e.getId());
            dto.setDateEmprunt(e.getDateEmprunt());
            dto.setDateRetourPrevue(e.getDateRetourPrevue());
            dto.setDateRetourEffective(e.getDateRetourEffective());
            dto.setEnCours(e.isEnCours());
            dto.setEmpruntePar(e.getAbonne());
            result.add(dto);
        }
        return result;
    }

    public List<Emprunt> findByPeriode(LocalDate debut, LocalDate fin) {
        return empruntRepository.findByDateEmpruntBetween(debut, fin);
    }

    public List<Emprunt> findEnCours() {
        return empruntRepository.findByDateRetourEffectiveIsNull();
    }

    public List<Emprunt> findEnRetard() {
        return empruntRepository.findEmpruntsEnRetard(LocalDate.now());
    }

    public List<Livre> findLivresDisponiblesPendant(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null || fin.isBefore(debut)) {
            throw new RuntimeException("periode invalide: debut et fin sont obligatoires");
        }
        Set<Long> empruntes = new HashSet<>(empruntRepository.findLivreIdsEmpruntesPendant(debut, fin));
        List<Livre> disponibles = new ArrayList<>();
        for (Livre livre : livreRepository.findAll()) {
            if (!empruntes.contains(livre.getId())) {
                disponibles.add(livre);
            }
        }
        return disponibles;
    }

    public LivreDisponibiliteDto disponibiliteLivre(Long livreId) {
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre not found: " + livreId));
        return buildDisponibilite(livre);
    }

    public List<LivreDisponibiliteDto> searchAvecDisponibilite(String q) {
        List<Livre> livres = livreRepository
                .findByTitreContainingIgnoreCaseOrAuteurContainingIgnoreCase(q, q);
        List<LivreDisponibiliteDto> result = new ArrayList<>();
        for (Livre livre : livres) {
            result.add(buildDisponibilite(livre));
        }
        return result;
    }

    public LivreDisponibiliteDto buildDisponibilite(Livre livre) {
        LivreDisponibiliteDto dto = new LivreDisponibiliteDto();
        dto.setLivre(livre);

        if (livre.isDisponibilite()) {
            dto.setDisponible(true);
            dto.setMessage("Livre disponible");
            return dto;
        }

        Emprunt enCours = empruntRepository
                .findFirstByLivreIdAndDateRetourEffectiveIsNullOrderByDateEmpruntDesc(livre.getId())
                .orElse(null);

        dto.setDisponible(false);
        if (enCours == null) {
            dto.setMessage("Livre non disponible");
            return dto;
        }

        LocalDate dispoLe = enCours.getDateRetourPrevue();
        long jours = ChronoUnit.DAYS.between(LocalDate.now(), dispoLe);
        if (jours < 0) {
            jours = 0;
        }

        dto.setDisponibleLe(dispoLe);
        dto.setJoursRestants(jours);
        dto.setDateEmprunt(enCours.getDateEmprunt());
        dto.setEmpruntePar(enCours.getAbonne());
        dto.setMessage("Ce livre n'est pas disponible. Il sera disponible apres "
                + jours + " jour(s) (le " + dispoLe + ")");
        return dto;
    }

    public void delete(Long id) {
        Emprunt emprunt = findById(id);
        if (emprunt.getDateRetourEffective() == null) {
            emprunt.getLivre().setDisponibilite(true);
        }
        empruntRepository.delete(emprunt);
    }
}
