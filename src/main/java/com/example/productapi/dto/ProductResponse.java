package com.example.productapi.dto;

import com.example.productapi.model.Product;

// DTO de sortie
public record ProductResponse(Long id, String name, String description, double price, int quantity) {

    public static ProductResponse from(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getQuantity());
    }
}
