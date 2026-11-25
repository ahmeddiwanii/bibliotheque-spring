# Documentation API — Bibliothèque

Base URL : `http://localhost:8080`

Les requêtes d'écriture utilisent `Content-Type: application/json`.

---

## Abonnés

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/abonnes` | Liste de tous les abonnés |
| GET | `/api/abonnes/{id}` | Détail d'un abonné |
| GET | `/api/abonnes/search?q=` | Recherche sur nom ou prénom |
| GET | `/api/abonnes/nom?nom=` | Filtre par nom |
| GET | `/api/abonnes/etudiants` | Liste des étudiants |
| GET | `/api/abonnes/etudiants/niveau?niveau=` | Étudiants par niveau |
| POST | `/api/abonnes/etudiants` | Créer un étudiant |
| PUT | `/api/abonnes/etudiants/{id}` | Modifier un étudiant |
| GET | `/api/abonnes/enseignants` | Liste des enseignants |
| GET | `/api/abonnes/enseignants/specialite?q=` | Enseignants par spécialité |
| POST | `/api/abonnes/enseignants` | Créer un enseignant |
| PUT | `/api/abonnes/enseignants/{id}` | Modifier un enseignant |
| GET | `/api/abonnes/groupes` | Liste des groupes |
| GET | `/api/abonnes/groupes/search?nomGroupe=` | Groupes par nom |
| POST | `/api/abonnes/groupes` | Créer un groupe |
| PUT | `/api/abonnes/groupes/{id}` | Modifier un groupe |
| DELETE | `/api/abonnes/{id}` | Supprimer un abonné |

```bat
curl http://localhost:8080/api/abonnes
curl "http://localhost:8080/api/abonnes/search?q=ali"
curl -X POST http://localhost:8080/api/abonnes/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"Ali\",\"prenom\":\"Ben\",\"niveau\":\"L3\"}"
curl -X POST http://localhost:8080/api/abonnes/enseignants -H "Content-Type: application/json" -d "{\"nom\":\"Sara\",\"prenom\":\"Amine\",\"specialite\":\"Math\"}"
curl -X POST http://localhost:8080/api/abonnes/groupes -H "Content-Type: application/json" -d "{\"nom\":\"Club\",\"prenom\":\"Info\",\"nomGroupe\":\"Groupe A\"}"
curl -X PUT http://localhost:8080/api/abonnes/etudiants/1 -H "Content-Type: application/json" -d "{\"nom\":\"Ali\",\"prenom\":\"Ben\",\"niveau\":\"M1\"}"
curl -X DELETE http://localhost:8080/api/abonnes/1
```

---

## Catégories

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/categories` | Liste |
| GET | `/api/categories/{id}` | Détail |
| GET | `/api/categories/search?name=` | Recherche partielle |
| GET | `/api/categories/name?name=` | Recherche exacte |
| POST | `/api/categories` | Création |
| PUT | `/api/categories/{id}` | Modification |
| DELETE | `/api/categories/{id}` | Suppression |

```bat
curl -X POST http://localhost:8080/api/categories -H "Content-Type: application/json" -d "{\"name\":\"Informatique\"}"
curl "http://localhost:8080/api/categories/search?name=Info"
curl -X PUT http://localhost:8080/api/categories/1 -H "Content-Type: application/json" -d "{\"name\":\"Science\"}"
curl -X DELETE http://localhost:8080/api/categories/1
```

---

## Livres

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/livres` | Liste |
| GET | `/api/livres/{id}` | Détail |
| GET | `/api/livres/search?q=` | Recherche titre / auteur |
| GET | `/api/livres/search-dispo?q=` | Recherche avec info de disponibilité |
| GET | `/api/livres/{id}/historique` | Historique des emprunts (qui / quand) |
| GET | `/api/livres/{id}/disponibilite` | Statut + date de retour prévue |
| GET | `/api/livres/disponibles-periode?debut=&fin=` | Livres libres sur une période |
| GET | `/api/livres/titre?titre=` | Filtre par titre |
| GET | `/api/livres/auteur?auteur=` | Filtre par auteur |
| GET | `/api/livres/disponibles` | Livres disponibles |
| GET | `/api/livres/indisponibles` | Livres empruntés |
| GET | `/api/livres/categorie/{id}` | Livres d'une catégorie |
| GET | `/api/livres?disponibilite=true` | Filtre disponibilité |
| GET | `/api/livres?categorieId=1&disponibilite=true` | Filtres combinés |
| POST | `/api/livres` | Création |
| PUT | `/api/livres/{id}` | Modification |
| DELETE | `/api/livres/{id}` | Suppression |

```bat
curl -X POST http://localhost:8080/api/livres -H "Content-Type: application/json" -d "{\"titre\":\"Java\",\"auteur\":\"Oracle\",\"disponibilite\":true,\"categorie\":{\"id\":1}}"
curl "http://localhost:8080/api/livres/search?q=java"
curl "http://localhost:8080/api/livres/search-dispo?q=java"
curl http://localhost:8080/api/livres/1/historique
curl http://localhost:8080/api/livres/1/disponibilite
curl "http://localhost:8080/api/livres/disponibles-periode?debut=2026-11-01&fin=2026-11-30"
curl http://localhost:8080/api/livres/disponibles
curl -X PUT http://localhost:8080/api/livres/1 -H "Content-Type: application/json" -d "{\"titre\":\"Java\",\"auteur\":\"Oracle\",\"disponibilite\":true,\"categorie\":{\"id\":1}}"
curl -X DELETE http://localhost:8080/api/livres/1
```

Exemple de réponse `search-dispo` si le livre est emprunté :

```json
{
  "disponible": false,
  "joursRestants": 5,
  "disponibleLe": "2026-12-01",
  "message": "Ce livre n'est pas disponible. Il sera disponible apres 5 jour(s) (le 2026-12-01)",
  "empruntePar": { "id": 1, "nom": "Ali", "prenom": "Ben" },
  "dateEmprunt": "2026-11-18",
  "livre": { "id": 1, "titre": "Java", "auteur": "Oracle", "disponibilite": false }
}
```

---

## Emprunts

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/emprunts` | Liste |
| GET | `/api/emprunts/{id}` | Détail |
| GET | `/api/emprunts/en-cours` | Emprunts non rendus |
| GET | `/api/emprunts/en-retard` | Emprunts en retard |
| GET | `/api/emprunts/periode?debut=&fin=` | Emprunts par période |
| GET | `/api/emprunts/abonne/{id}` | Emprunts d'un abonné |
| GET | `/api/emprunts/livre/{id}` | Emprunts d'un livre |
| POST | `/api/emprunts` | Emprunter un livre |
| PUT | `/api/emprunts/{id}/retour` | Rendre un livre |
| DELETE | `/api/emprunts/{id}` | Supprimer un emprunt |

Champs utiles :

- `dateEmprunt` : date de l'emprunt
- `dateRetourPrevue` : date prévue de retour
- `dateRetourEffective` : date réelle de retour (`null` si en cours)

```bat
curl -X POST http://localhost:8080/api/emprunts -H "Content-Type: application/json" -d "{\"abonneId\":1,\"livreId\":1,\"dateRetour\":\"2026-12-10\"}"
curl http://localhost:8080/api/emprunts/en-cours
curl http://localhost:8080/api/emprunts/abonne/1
curl http://localhost:8080/api/emprunts/livre/1
curl -X PUT http://localhost:8080/api/emprunts/1/retour
curl -X DELETE http://localhost:8080/api/emprunts/1
```

---

## Codes HTTP

| Code | Signification |
|------|----------------|
| 200 | Succès |
| 201 | Création |
| 204 | Suppression |
| 400 | Erreur métier / requête invalide |
