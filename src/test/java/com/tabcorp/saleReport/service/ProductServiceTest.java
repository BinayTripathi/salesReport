package com.tabcorp.saleReport.service;

import com.tabcorp.saleReport.model.Product;
import com.tabcorp.saleReport.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.mockito.Mockito.when;

@Service
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getProductByCode_should_return_product_by_id() {
        Product product = new Product("PRODUCT_001", 50.0, "Active");
        when(productRepository.findById("PRODUCT_001")).thenReturn(Optional.of(product));

        Mono<Product> result = productService.getProductById("PRODUCT_001");

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();

    }
}
