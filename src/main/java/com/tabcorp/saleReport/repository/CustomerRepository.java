package com.tabcorp.saleReport.repository;

import com.tabcorp.saleReport.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
