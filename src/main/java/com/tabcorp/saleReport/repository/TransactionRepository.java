package com.tabcorp.saleReport.repository;

import com.tabcorp.saleReport.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t.customerId, t.cost FROM Transaction t GROUP BY t.customerId")
    List<Object[]> findTotalCostPerCustomer();

    @Query("SELECT t.productCode, t.cost FROM Transaction t  GROUP BY t.productCode")
    List<Object[]> findTotalCostPerProduct();

    @Query("SELECT COUNT(t) FROM Transaction t JOIN Customer c ON t.customerId = c.id WHERE c.location = 'Australia'")
    long countTransactionsForAustralianCustomers();
}
