package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + id));
    }

    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }

        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
    }



}
