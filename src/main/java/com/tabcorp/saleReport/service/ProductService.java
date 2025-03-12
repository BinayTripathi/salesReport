package com.tabcorp.saleReport.service;

import com.tabcorp.saleReport.model.Product;
import com.tabcorp.saleReport.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Mono<Product> getProductById(String id) {
        return Mono.defer(() -> Mono.justOrEmpty(productRepository.findById(id)));
    }

    public Mono<Product> saveProduct(Product product) {
        return Mono.defer(() ->Mono.just(productRepository.save(product)));
    }
}
