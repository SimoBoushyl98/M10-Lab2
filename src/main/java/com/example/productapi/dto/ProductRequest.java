package com.example.productapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// DTO d'entrée (exercice 3 : record au lieu d'une classe)
public record ProductRequest(

        @NotBlank(message = "Le nom est obligatoire")
        @Size(min = 2, max = 100, message = "Le nom doit faire entre 2 et 100 caractères")
        String name,

        @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
        String description,

        @NotNull(message = "Le prix est obligatoire")
        @Positive(message = "Le prix doit être supérieur à 0")
        Double price,

        // pas de @Max ici : la limite de 1000 est gérée par InvalidQuantityException
        @NotNull(message = "La quantité est obligatoire")
        @Min(value = 0, message = "La quantité ne peut pas être négative")
        Integer quantity
) {
}
