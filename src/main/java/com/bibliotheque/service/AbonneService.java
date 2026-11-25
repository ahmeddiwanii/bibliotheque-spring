package com.bibliotheque.service;

import com.bibliotheque.entity.*;
import com.bibliotheque.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Services liés aux abonnés (CRUD et recherches).
 * Les sous-types sont gérés via leurs repositories dédiés.
 */
@Service
@Transactional
public class AbonneService {

    private final AbonneRepository abonneRepository;
    private final EnseignantRepository enseignantRepository;
    private final EtudiantRepository etudiantRepository;
    private final GroupeRepository groupeRepository;

    public AbonneService(AbonneRepository abonneRepository,
                         EnseignantRepository enseignantRepository,
                         EtudiantRepository etudiantRepository,
                         GroupeRepository groupeRepository) {
        this.abonneRepository = abonneRepository;
        this.enseignantRepository = enseignantRepository;
        this.etudiantRepository = etudiantRepository;
        this.groupeRepository = groupeRepository;
    }

    public List<Abonne> findAll() {
        return abonneRepository.findAll();
    }

    public Abonne findById(Long id) {
        return abonneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Abonne not found: " + id));
    }

    public List<Abonne> search(String q) {
        return abonneRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(q, q);
    }

    public List<Abonne> findByNom(String nom) {
        return abonneRepository.findByNomContainingIgnoreCase(nom);
    }

    public Enseignant saveEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    public Enseignant updateEnseignant(Long id, Enseignant data) {
        Enseignant existing = enseignantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant not found: " + id));
        existing.setNom(data.getNom());
        existing.setPrenom(data.getPrenom());
        existing.setSpecialite(data.getSpecialite());
        return enseignantRepository.save(existing);
    }

    public Etudiant saveEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public Etudiant updateEtudiant(Long id, Etudiant data) {
        Etudiant existing = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant not found: " + id));
        existing.setNom(data.getNom());
        existing.setPrenom(data.getPrenom());
        existing.setNiveau(data.getNiveau());
        return etudiantRepository.save(existing);
    }

    public Groupe saveGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    public Groupe updateGroupe(Long id, Groupe data) {
        Groupe existing = groupeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Groupe not found: " + id));
        existing.setNom(data.getNom());
        existing.setPrenom(data.getPrenom());
        existing.setNomGroupe(data.getNomGroupe());
        return groupeRepository.save(existing);
    }

    public List<Enseignant> findAllEnseignants() {
        return enseignantRepository.findAll();
    }

    public List<Enseignant> findEnseignantsBySpecialite(String specialite) {
        return enseignantRepository.findBySpecialiteContainingIgnoreCase(specialite);
    }

    public List<Etudiant> findAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public List<Etudiant> findEtudiantsByNiveau(String niveau) {
        return etudiantRepository.findByNiveauIgnoreCase(niveau);
    }

    public List<Groupe> findAllGroupes() {
        return groupeRepository.findAll();
    }

    public List<Groupe> findGroupesByNom(String nomGroupe) {
        return groupeRepository.findByNomGroupeContainingIgnoreCase(nomGroupe);
    }

    public void delete(Long id) {
        if (!abonneRepository.existsById(id)) {
            throw new RuntimeException("Abonne not found: " + id);
        }
        abonneRepository.deleteById(id);
    }
}
