package com.tabcorp.saleReport.controller;

import com.tabcorp.saleReport.model.Product;
import com.tabcorp.saleReport.model.Transaction;
import com.tabcorp.saleReport.service.ProductService;
import com.tabcorp.saleReport.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction_Successful() {
        // Mock data
        Transaction transaction = new Transaction();
        transaction.setTransactionTime(LocalDateTime.now().plusHours(1)); // Future date
        transaction.setQuantity(2);
        transaction.setProductCode("PRODUCT_001");

        Product product = new Product();
        product.setStatus("Active");
        product.setCost(100.0);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setTransactionTime(transaction.getTransactionTime());
        savedTransaction.setQuantity(transaction.getQuantity());
        savedTransaction.setProductCode(transaction.getProductCode());
        savedTransaction.setCost(200.0); // Total cost: 2 * 100

        when(productService.getProductById("PRODUCT_001")).thenReturn(Mono.just(product));
        when(transactionService.saveTransaction(transaction)).thenReturn(Mono.just(savedTransaction));

        // Test
        Mono<Transaction> result = transactionController.createTransaction(transaction);

        StepVerifier.create(result)
                .expectNextMatches(t -> t.getCost() == 200.0)
                .verifyComplete();

        // Verify interactions
        verify(productService, times(1)).getProductById("PRODUCT_001");
        verify(transactionService, times(1)).saveTransaction(transaction);
    }

    @Test
    public void testCreateTransaction_should_throw_illegal_args_exception_if_date_is_in_the_past() {
        // Mock data
        Transaction transaction = new Transaction();
        transaction.setTransactionTime(LocalDateTime.now().minusHours(1)); // Past date

        // Test
        Mono<Transaction> result = transactionController.createTransaction(transaction);

        StepVerifier.create(result)
                .expectErrorMatches(error -> error instanceof IllegalArgumentException &&
                        error.getMessage().equals("Transaction date must not be in the past."))
                .verify();

        // Verify no interactions with services
        verifyNoInteractions(productService);
        verifyNoInteractions(transactionService);
    }

    @Test
    public void testCreateTransaction__should_throw_illegal_args_exception_if_product_is_inactive() {
        // Mock data
        Transaction transaction = new Transaction();
        transaction.setTransactionTime(LocalDateTime.now().plusHours(1)); // Future date
        transaction.setQuantity(2);
        transaction.setProductCode("PRODUCT_001");

        Product product = new Product();
        product.setStatus("Inactive"); // Inactive product

        when(productService.getProductById("PRODUCT_001")).thenReturn(Mono.just(product));

        // Test
        Mono<Transaction> result = transactionController.createTransaction(transaction);

        StepVerifier.create(result)
                .expectErrorMatches(error -> error instanceof IllegalArgumentException &&
                        error.getMessage().equals("Product must be active."))
                .verify();

        // Verify interactions
        verify(productService, times(1)).getProductById("PRODUCT_001");
        verifyNoInteractions(transactionService);
    }

    @Test
    public void testCreateTransaction__should_throw_illegal_args_exception_if_cost_exceeds_5000() {
        // Mock data
        Transaction transaction = new Transaction();
        transaction.setTransactionTime(LocalDateTime.now().plusHours(1)); // Future date
        transaction.setQuantity(100); // Large quantity
        transaction.setProductCode("PRODUCT_001");

        Product product = new Product();
        product.setStatus("Active");
        product.setCost(51.0); // Cost per item

        when(productService.getProductById("PRODUCT_001")).thenReturn(Mono.just(product));

        // Test
        Mono<Transaction> result = transactionController.createTransaction(transaction);

        StepVerifier.create(result)
                .expectErrorMatches(error -> error instanceof IllegalArgumentException &&
                        error.getMessage().equals("Total cost of transaction must not exceed 5000."))
                .verify();

        // Verify interactions
        verify(productService, times(1)).getProductById("PRODUCT_001");
        verifyNoInteractions(transactionService);
    }

    @Test
    public void testGetTotalCostPerCustomer_should_return_total_cost_per_customer() {
        // Mock data
        List<Object[]> totalCostPerCustomer = Arrays.asList(
                new Object[]{"Customer_1", 200.0},
                new Object[]{"Customer_2", 300.0}
        );

        when(transactionService.getTotalCostPerCustomer()).thenReturn(Mono.just(totalCostPerCustomer));

        // Test
        Mono<List<Object[]>> result = transactionController.getTotalCostPerCustomer();

        StepVerifier.create(result)
                .expectNext(totalCostPerCustomer)
                .verifyComplete();

        // Verify interactions
        verify(transactionService, times(1)).getTotalCostPerCustomer();
    }

    @Test
    public void testGetTotalCostPerProduct_should_return_total_cost_per_product() {
        // Mock data
        List<Object[]> totalCostPerProduct = Arrays.asList(
                new Object[]{"PRODUCT_001", 400.0},
                new Object[]{"PRODUCT_002", 600.0}
        );

        when(transactionService.getTotalCostPerProduct()).thenReturn(Mono.just(totalCostPerProduct));

        // Test
        Mono<List<Object[]>> result = transactionController.getTotalCostPerProduct();

        StepVerifier.create(result)
                .expectNext(totalCostPerProduct)
                .verifyComplete();

        // Verify interactions
        verify(transactionService, times(1)).getTotalCostPerProduct();
    }

    @Test
    public void getCountTransactionsForAustralianCustomers_should_return_number_of_australian_customers() {
        // Mock data
        long count = 5L;

        when(transactionService.getCountTransactionsForAustralianCustomers()).thenReturn(Mono.just(count));

        // Test
        Mono<Long> result = transactionController.getCountTransactionsForAustralianCustomers();

        StepVerifier.create(result)
                .expectNext(count)
                .verifyComplete();

        // Verify interactions
        verify(transactionService, times(1)).getCountTransactionsForAustralianCustomers();
    }
}
