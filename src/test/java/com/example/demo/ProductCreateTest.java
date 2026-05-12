package com.example.demo;


import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCreateTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldSaveProductSuccessfully() {
        Product inputProduct = new Product();
        inputProduct.setName("Klawiatura");

        Product savedProduct = new Product();
        savedProduct.setId(100L);
        savedProduct.setName("Klawiatura");

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.createProduct(inputProduct);

        assertNotNull(result.getId(), "Zapisany produkt powinien mieć ID");
        assertEquals(100L, result.getId());
        assertEquals("Klawiatura", result.getName());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNameIsEmpty() {
        // Given
        Product invalidProduct = new Product();
        invalidProduct.setName("");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(invalidProduct);
        });

        verify(productRepository, never()).save(any(Product.class));
    }
}

