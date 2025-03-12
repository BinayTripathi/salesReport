package com.tabcorp.saleReport.service;

import com.tabcorp.saleReport.model.Transaction;
import com.tabcorp.saleReport.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Mono<Transaction> saveTransaction(Transaction transaction) {
        return Mono.defer(() -> Mono.just(transactionRepository.save(transaction)));
    }

    public Mono<List<Object[]>> getTotalCostPerCustomer() {
        return Mono.fromSupplier(() -> transactionRepository.findTotalCostPerCustomer());
    }

    public Mono<List<Object[]>> getTotalCostPerProduct() {
        return Mono.fromSupplier(() -> transactionRepository.findTotalCostPerProduct());
    }

    public Mono<Long> getCountTransactionsForAustralianCustomers() {
        return Mono.fromSupplier(transactionRepository::countTransactionsForAustralianCustomers);
    }
}
