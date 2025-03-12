package com.tabcorp.saleReport.repository;

import com.tabcorp.saleReport.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t.customerId, SUM(t.quantity * p.cost) FROM Transaction t JOIN Product p ON t.productCode = p.productCode GROUP BY t.customerId")
    List<Object[]> findTotalCostPerCustomer();

    @Query("SELECT t.productCode, SUM(t.quantity * p.cost) FROM Transaction t JOIN Product p ON t.productCode = p.productCode GROUP BY t.productCode")
    List<Object[]> findTotalCostPerProduct();

    @Query("SELECT COUNT(t) FROM Transaction t JOIN Customer c ON t.customerId = c.id WHERE c.location = 'Australia'")
    long countTransactionsForAustralianCustomers();
}
