package com.tabcorp.saleReport.service;

import com.tabcorp.saleReport.model.Transaction;
import com.tabcorp.saleReport.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;

    public Mono<Transaction> saveTransaction(Transaction transaction) {
        return Mono.defer(() -> Mono.just(transactionRepository.save(transaction)));
    }

    public Mono<List<Object[]>> getTotalCostPerCustomer() {
        return Mono.defer(() -> Mono.just(transactionRepository.findTotalCostPerCustomer()));
    }

    public Mono<List<Object[]>> getTotalCostPerProduct() {
        return Mono.defer(() -> Mono.just(transactionRepository.findTotalCostPerProduct()));
    }

    public Mono<Long> getCountTransactionsForAustralianCustomers() {
        return Mono.defer(() -> Mono.just(transactionRepository.countTransactionsForAustralianCustomers()));
    }
}
