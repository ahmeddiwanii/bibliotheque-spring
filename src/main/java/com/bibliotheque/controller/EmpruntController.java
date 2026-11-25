package com.bibliotheque.controller;

import com.bibliotheque.entity.Emprunt;
import com.bibliotheque.service.EmpruntService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/** Endpoints REST pour emprunter et rendre des livres. */
@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;

    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    @GetMapping
    public List<Emprunt> getAll() {
        return empruntService.findAll();
    }

    @GetMapping("/en-cours")
    public List<Emprunt> getEnCours() {
        return empruntService.findEnCours();
    }

    @GetMapping("/en-retard")
    public List<Emprunt> getEnRetard() {
        return empruntService.findEnRetard();
    }

    @GetMapping("/periode")
    public List<Emprunt> getByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return empruntService.findByPeriode(debut, fin);
    }

    @GetMapping("/abonne/{abonneId}")
    public List<Emprunt> getByAbonne(@PathVariable Long abonneId) {
        return empruntService.findByAbonne(abonneId);
    }

    @GetMapping("/livre/{livreId}")
    public List<Emprunt> getByLivre(@PathVariable Long livreId) {
        return empruntService.findByLivre(livreId);
    }

    @GetMapping("/{id}")
    public Emprunt getById(@PathVariable Long id) {
        return empruntService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Emprunt emprunter(@RequestBody Map<String, Object> body) {
        if (body.get("abonneId") == null || body.get("livreId") == null) {
            throw new RuntimeException("abonneId et livreId sont obligatoires");
        }
        Long abonneId = Long.valueOf(body.get("abonneId").toString());
        Long livreId = Long.valueOf(body.get("livreId").toString());
        Object rawDate = body.get("dateRetourPrevue") != null
                ? body.get("dateRetourPrevue")
                : body.get("dateRetour");
        LocalDate dateRetourPrevue = rawDate != null
                ? LocalDate.parse(rawDate.toString())
                : LocalDate.now().plusDays(15);
        return empruntService.emprunter(abonneId, livreId, dateRetourPrevue);
    }

    @PutMapping("/{id}/retour")
    public Emprunt retourner(@PathVariable Long id) {
        return empruntService.retourner(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        empruntService.delete(id);
    }
}
