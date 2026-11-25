@echo off
setlocal EnableDelayedExpansion
set BASE=http://localhost:8080

echo.
echo ========== 1) INSERT (seed data) ==========
echo.

echo [insert categorie]
curl -s -X POST %BASE%/api/categories -H "Content-Type: application/json" -d "{\"name\":\"Informatique\"}"
echo.

echo [insert etudiant]
curl -s -X POST %BASE%/api/abonnes/etudiants -H "Content-Type: application/json" -d "{\"nom\":\"Ali\",\"prenom\":\"Ben\",\"niveau\":\"L3\"}"
echo.

echo [insert enseignant]
curl -s -X POST %BASE%/api/abonnes/enseignants -H "Content-Type: application/json" -d "{\"nom\":\"Sara\",\"prenom\":\"Amine\",\"specialite\":\"Math\"}"
echo.

echo [insert groupe]
curl -s -X POST %BASE%/api/abonnes/groupes -H "Content-Type: application/json" -d "{\"nom\":\"Club\",\"prenom\":\"Info\",\"nomGroupe\":\"Groupe A\"}"
echo.

echo [insert livre]
curl -s -X POST %BASE%/api/livres -H "Content-Type: application/json" -d "{\"titre\":\"Java\",\"auteur\":\"Oracle\",\"disponibilite\":true,\"categorie\":{\"id\":1}}"
echo.

echo [insert emprunt]
curl -s -X POST %BASE%/api/emprunts -H "Content-Type: application/json" -d "{\"abonneId\":1,\"livreId\":1,\"dateRetour\":\"2026-11-26\"}"
echo.

echo.
echo ========== 2) FIND / SEARCH (loop) ==========
echo.

for %%U in (
  "/api/abonnes"
  "/api/abonnes/1"
  "/api/abonnes/search?q=ali"
  "/api/abonnes/nom?nom=Ali"
  "/api/abonnes/etudiants"
  "/api/abonnes/etudiants/niveau?niveau=L3"
  "/api/abonnes/enseignants"
  "/api/abonnes/enseignants/specialite?q=Math"
  "/api/abonnes/groupes"
  "/api/abonnes/groupes/search?nomGroupe=A"
  "/api/categories"
  "/api/categories/1"
  "/api/categories/search?name=Info"
  "/api/categories/name?name=Informatique"
  "/api/livres"
  "/api/livres/1"
  "/api/livres/search?q=java"
  "/api/livres/titre?titre=Java"
  "/api/livres/auteur?auteur=Oracle"
  "/api/livres/disponibles"
  "/api/livres/indisponibles"
  "/api/livres/categorie/1"
  "/api/livres?disponibilite=true"
  "/api/livres?categorieId=1&disponibilite=true"
  "/api/emprunts"
  "/api/emprunts/1"
  "/api/emprunts/en-cours"
  "/api/emprunts/en-retard"
  "/api/emprunts/periode?debut=2026-11-01&fin=2026-11-30"
  "/api/emprunts/abonne/1"
  "/api/emprunts/livre/1"
) do (
  echo [GET %%U]
  curl -s "%BASE%%%U"
  echo.
  echo.
)

echo.
echo ========== 3) UPDATE ==========
echo.

echo [update etudiant]
curl -s -X PUT %BASE%/api/abonnes/etudiants/1 -H "Content-Type: application/json" -d "{\"nom\":\"Ali\",\"prenom\":\"Ben\",\"niveau\":\"M1\"}"
echo.

echo [update enseignant]
curl -s -X PUT %BASE%/api/abonnes/enseignants/2 -H "Content-Type: application/json" -d "{\"nom\":\"Sara\",\"prenom\":\"Amine\",\"specialite\":\"Physique\"}"
echo.

echo [update groupe]
curl -s -X PUT %BASE%/api/abonnes/groupes/3 -H "Content-Type: application/json" -d "{\"nom\":\"Club\",\"prenom\":\"Info\",\"nomGroupe\":\"Groupe B\"}"
echo.

echo [update categorie]
curl -s -X PUT %BASE%/api/categories/1 -H "Content-Type: application/json" -d "{\"name\":\"Science\"}"
echo.

echo [update livre]
curl -s -X PUT %BASE%/api/livres/1 -H "Content-Type: application/json" -d "{\"titre\":\"Java\",\"auteur\":\"Oracle\",\"disponibilite\":false,\"categorie\":{\"id\":1}}"
echo.

echo [return book]
curl -s -X PUT %BASE%/api/emprunts/1/retour
echo.

echo.
echo ========== 4) DELETE ==========
echo.

echo [delete emprunt]
curl -s -X DELETE %BASE%/api/emprunts/1
echo.

echo [delete livre]
curl -s -X DELETE %BASE%/api/livres/1
echo.

echo [delete abonne etudiant]
curl -s -X DELETE %BASE%/api/abonnes/1
echo.

echo [delete enseignant]
curl -s -X DELETE %BASE%/api/abonnes/2
echo.

echo [delete groupe]
curl -s -X DELETE %BASE%/api/abonnes/3
echo.

echo [delete categorie]
curl -s -X DELETE %BASE%/api/categories/1
echo.

echo.
echo ========== DONE ==========
pause
