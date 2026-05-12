package com.example.demo;


import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnProductWhenFound() {
        // Given
        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setName("Laptop");

        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        // When
        Product result = productService.getProductById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Laptop", result.getName());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Given
        when(productRepository.findById(99L)).thenReturn(Optional.empty());


        assertThrows(NoSuchElementException.class, () -> {
            productService.getProductById(99L);
        });

        verify(productRepository, times(1)).findById(99L);
    }
}