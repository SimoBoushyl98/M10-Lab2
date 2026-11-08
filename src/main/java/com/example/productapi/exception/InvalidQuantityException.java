package com.example.productapi.exception;

// Exercice 2 : levée quand la quantité dépasse la limite autorisée
public class InvalidQuantityException extends RuntimeException {

    public InvalidQuantityException(int quantity, int max) {
        super("Quantité invalide : " + quantity + " (maximum autorisé : " + max + ")");
    }
}
