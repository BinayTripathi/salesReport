package com.tabcorp.saleReport.service;


import com.tabcorp.saleReport.model.Customer;
import com.tabcorp.saleReport.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Service
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllCustomers_should_return_all_customers() {
        Customer customer1 = new Customer(1L, "Ron", "Weasly", "ron.weasly@gmail.com", "Australia");
        Customer customer2 = new Customer(2L, "Harry", "Potter", "harry.potter@gmail.com", "US");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        Flux<Customer> customers = customerService.getAllCustomers();

        StepVerifier.create(customers)
                .expectNext(customer1)
                .expectNext(customer2)
                .verifyComplete();
    }

    @Test
    public void testGetCustomerById_should_return_customer_by_id() {
        Customer customer = new Customer(1L, "Ron", "Weasly", "ron.weasly@gmail.com", "Australia");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Mono<Customer> result = customerService.getCustomerById(1L);

        StepVerifier.create(result)
                .expectNext(customer)
                .verifyComplete();
    }


}
