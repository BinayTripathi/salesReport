package com.tabcorp.saleReport.service;

import com.tabcorp.saleReport.model.Transaction;
import com.tabcorp.saleReport.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveTransaction_should_save_transaction_and_return_transaction() {
        Transaction transaction = new Transaction(1L, LocalDateTime.now(), 104567L, 1, "PRODUCT_001", 600.00);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Mono<Transaction> result = transactionService.saveTransaction(transaction);

        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();
    }

    @Test
    public void getCountTransactionsForAustralianCustomers_should_return_total_no_of_australian_customers() {
        when(transactionRepository.countTransactionsForAustralianCustomers()).thenReturn(1L);

        Mono<Long> count = transactionService.getCountTransactionsForAustralianCustomers();

        StepVerifier.create(count)
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void getTotalCostPerCustomer_should_return_total_cost_per_customer() {

        List<Object[]> mockQueryResponse = new ArrayList<>();
        mockQueryResponse.add(new Object[]{"Customer1", 100.0}); // Example: Name and TotalCost
        mockQueryResponse.add(new Object[]{"Customer2", 200.5});
        mockQueryResponse.add(new Object[]{"Customer3", 300.75});

        when(transactionRepository.findTotalCostPerCustomer()).thenReturn(mockQueryResponse);

        Mono<List<Object[]>> result = transactionService.getTotalCostPerCustomer();

        assertEquals(mockQueryResponse, result.block());
        verify(transactionRepository, times(1)).findTotalCostPerCustomer();
    }

    @Test
    void getTotalCostPerProduct_should_return_total_cost_per_product() {
        List<Object[]> mockQueryResponse = new ArrayList<>();
        mockQueryResponse.add(new Object[]{"Product1", 50.0});

        when(transactionRepository.findTotalCostPerProduct()).thenReturn(mockQueryResponse);

        Mono<List<Object[]>> result = transactionService.getTotalCostPerProduct();

        assertEquals(mockQueryResponse, result.block());
        verify(transactionRepository, times(1)).findTotalCostPerProduct();
    }
}

