# Gestion de bibliothèque

API REST Spring Boot pour la gestion d'une bibliothèque : abonnés, livres, catégories et emprunts.

## Stack

- Java 21
- Spring Boot 3.4
- Spring Data JPA
- H2 (base en mémoire)
- Maven

## Modèle

Héritage **SINGLE_TABLE** sur `Abonne` :

- `Etudiant` (niveau)
- `Enseignant` (specialite)
- `Groupe` (nomGroupe)

Relations :

- Une `Categorie` contient plusieurs `Livre`
- Un `Abonne` peut avoir plusieurs `Emprunt`
- Un `Livre` peut avoir plusieurs `Emprunt`

## Lancer le projet

```bat
cd project
mvn spring-boot:run
```

API : http://localhost:8080  
Console H2 : http://localhost:8080/h2-console  

- JDBC URL : `jdbc:h2:mem:bibliotheque`
- User : `sa`
- Password : (vide)

## Documentation API

Voir [API.md](API.md) pour la liste des endpoints et des exemples `curl`.

## Structure

```
src/main/java/com/bibliotheque/
  entity/        # entités JPA
  repository/    # Spring Data
  service/       # logique métier
  controller/    # endpoints REST
  dto/           # objets de réponse
```

## Exemple rapide

```bat
curl -X POST http://localhost:8080/api/categories -H "Content-Type: application/json" -d "{\"name\":\"Informatique\"}"
curl -X POST http://localhost:8080/api/abonnes/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"Ali\",\"prenom\":\"Ben\",\"niveau\":\"L3\"}"
curl -X POST http://localhost:8080/api/livres -H "Content-Type: application/json" -d "{\"titre\":\"Java\",\"auteur\":\"Oracle\",\"disponibilite\":true,\"categorie\":{\"id\":1}}"
curl -X POST http://localhost:8080/api/emprunts -H "Content-Type: application/json" -d "{\"abonneId\":1,\"livreId\":1,\"dateRetour\":\"2026-12-10\"}"
```
