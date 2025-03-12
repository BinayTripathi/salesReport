package com.tabcorp.saleReport.controller;


import com.tabcorp.saleReport.model.Product;
import com.tabcorp.saleReport.model.Transaction;
import com.tabcorp.saleReport.service.ProductService;
import com.tabcorp.saleReport.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public Mono<Transaction> createTransaction(
            @RequestParam("transactionTime") String transactionTime,
            @RequestParam("customerId") Long customerId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("productCode") String productCode) {

        LocalDateTime transactionDateTime = LocalDateTime.parse(transactionTime);

        // Validation: Date must not be in the past
        if (transactionDateTime.isBefore(LocalDateTime.now())) {
            return Mono.error(new IllegalArgumentException("Transaction date must not be in the past."));
        }

        return productService.getProductById(productCode)
                .flatMap(product -> {
                    // Validation: Product must be active
                    if (!"Active".equalsIgnoreCase(product.getStatus())) {
                        return Mono.error(new IllegalArgumentException("Product must be active."));
                    }

                    // Validation: Total cost of transaction must not exceed 5000
                    double totalCost = quantity * product.getCost();
                    if (totalCost > 5000) {
                        return Mono.error(new IllegalArgumentException("Total cost of transaction must not exceed 5000."));
                    }

                    Transaction transaction = new Transaction();
                    transaction.setTransactionTime(transactionDateTime);
                    transaction.setCustomerId(customerId);
                    transaction.setQuantity(quantity);
                    transaction.setProductCode(productCode);

                    return transactionService.saveTransaction(transaction);
                });
    }

    @GetMapping("/total-cost/customer")
    public Mono<List<Object[]>> getTotalCostPerCustomer() {
        return transactionService.getTotalCostPerCustomer();
    }

    @GetMapping("/total-cost/product")
    public Mono<List<Object[]>> getTotalCostPerProduct() {
        return transactionService.getTotalCostPerProduct();
    }

    @GetMapping("/count/australian-customers")
    public Mono<Long> getCountTransactionsForAustralianCustomers() {
        return transactionService.getCountTransactionsForAustralianCustomers();
    }
}
