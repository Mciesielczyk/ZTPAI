package com.example.demo.bojowe.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceBojoweTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void happyPath_shouldSaveProductAndReturnSavedEntity() {
        Product input = new Product();
        input.setName("Laptop");
        input.setPrice(3500.0);
        input.setDescription("Testowy produkt");

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Laptop");
        saved.setPrice(3500.0);
        saved.setDescription("Testowy produkt");

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        Product result = productService.createProduct(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(3500.0, result.getPrice());
        assertEquals("Testowy produkt", result.getDescription());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void negativePath_shouldThrowWhenProductIsMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.getProductById(99L));
        verify(productRepository, times(1)).findById(99L);
    }

    @Test
    void businessValidation_shouldRejectBlankName() {
        Product invalid = new Product();
        invalid.setName("   ");
        invalid.setPrice(1200.0);

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(invalid));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void happyPath_shouldReturnAllProductsFromRepository() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Myszka");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Klawiatura");

        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Product> result = productService.findAllProducts();

        assertEquals(2, result.size());
        assertEquals("Myszka", result.get(0).getName());
        assertEquals("Klawiatura", result.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }
}
