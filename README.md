# Lab 2 – API REST Produits (Spring Boot)

API REST de gestion de produits avec DTOs (records Java), validation et gestion centralisée des exceptions.
Les données sont stockées en mémoire (la base de données arrive au Lab 3).

## Prérequis

- Java 17 ou plus
- Maven 3.9+
- Postman (pour les tests)

## Lancer le projet

```bash
mvn spring-boot:run
```

Ou depuis l'IDE : lancer la classe `ProductApiApplication`.

L'API démarre sur `http://localhost:8080`. Quatre produits sont créés au démarrage pour pouvoir tester directement.

## Endpoints

| Méthode | URL | Description |
|---|---|---|
| GET | `/api/products` | Liste des produits |
| GET | `/api/products/{id}` | Détail d'un produit |
| GET | `/api/products/search?name=...` | Recherche par nom |
| POST | `/api/products` | Création |
| PUT | `/api/products/{id}` | Modification |
| DELETE | `/api/products/{id}` | Suppression |

Exemple de corps pour POST / PUT :

```json
{
  "name": "Casque Sony WH-1000XM5",
  "description": "Casque Bluetooth",
  "price": 3290.0,
  "quantity": 30
}
```

## Exercices réalisés

1. **Recherche par nom** : `GET /api/products/search?name=laptop`, insensible aux majuscules.
2. **InvalidQuantityException** : levée si `quantity > 1000` (en POST et en PUT), renvoie une 400.
3. **Records Java** : `ProductRequest`, `ProductResponse` et `ErrorResponse` sont des records.

## Codes de retour

| Code | Cas |
|---|---|
| 200 | Lecture ou modification réussie |
| 201 | Produit créé (avec le header `Location`) |
| 204 | Produit supprimé |
| 400 | Données invalides, quantité > 1000 ou paramètre manquant |
| 404 | Produit introuvable |

Exemple d'erreur :

```json
{
  "timestamp": "2026-11-08T19:30:12.451",
  "status": 400,
  "error": "Bad Request",
  "message": "Quantité invalide : 1500 (maximum autorisé : 1000)",
  "path": "/api/products"
}
```

## Tests Postman

1. Importer `postman/Lab2_Product_API.postman_collection.json`.
2. Lancer l'application.
3. Exécuter la collection avec le Collection Runner (dans l'ordre des dossiers).

La variable `baseUrl` est définie dans la collection (`http://localhost:8080`).

## Structure

```
src/main/java/com/example/productapi
├── controller/   ProductController
├── service/      ProductService (stockage en mémoire)
├── dto/          ProductRequest, ProductResponse, ErrorResponse
├── model/        Product
└── exception/    ProductNotFoundException, InvalidQuantityException, GlobalExceptionHandler
```
