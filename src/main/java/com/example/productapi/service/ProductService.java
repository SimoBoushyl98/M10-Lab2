package com.example.productapi.service;

import com.example.productapi.dto.ProductRequest;
import com.example.productapi.dto.ProductResponse;
import com.example.productapi.exception.InvalidQuantityException;
import com.example.productapi.exception.ProductNotFoundException;
import com.example.productapi.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    public static final int MAX_QUANTITY = 1000;

    // stockage en mémoire (la base de données arrive au Lab 3)
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public ProductService() {
        save(new Product(null, "Laptop Dell XPS 13", "Ultrabook 13 pouces", 12990.0, 15));
        save(new Product(null, "Souris Logitech MX Master", "Souris sans fil", 899.0, 40));
        save(new Product(null, "Clavier mécanique Keychron", "Clavier switches brown", 1250.0, 25));
        save(new Product(null, "Écran Samsung 27 pouces", "Écran 4K", 3490.0, 10));
    }

    public List<ProductResponse> findAll() {
        return products.values().stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse findById(Long id) {
        return ProductResponse.from(getOrThrow(id));
    }

    // Exercice 1 : recherche par nom (sans tenir compte des majuscules)
    public List<ProductResponse> searchByName(String name) {
        String keyword = name.trim().toLowerCase();
        return products.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword))
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse create(ProductRequest request) {
        checkQuantity(request.quantity());
        Product product = new Product(null, request.name(), request.description(), request.price(), request.quantity());
        return ProductResponse.from(save(product));
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getOrThrow(id);
        checkQuantity(request.quantity());
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        return ProductResponse.from(product);
    }

    public void delete(Long id) {
        if (products.remove(id) == null) {
            throw new ProductNotFoundException(id);
        }
    }

    private void checkQuantity(int quantity) {
        if (quantity > MAX_QUANTITY) {
            throw new InvalidQuantityException(quantity, MAX_QUANTITY);
        }
    }

    private Product getOrThrow(Long id) {
        Product product = products.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }

    private Product save(Product product) {
        product.setId(idGenerator.incrementAndGet());
        products.put(product.getId(), product);
        return product;
    }
}
