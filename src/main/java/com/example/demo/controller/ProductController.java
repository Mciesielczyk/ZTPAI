package com.example.demo.controller;

import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.findAllProducts()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        Product product = productService.createProduct(ProductMapper.toEntity(request));
        return ProductMapper.toResponse(product);
    }
}
