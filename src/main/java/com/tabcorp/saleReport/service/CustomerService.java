package com.tabcorp.saleReport.service;


import com.tabcorp.saleReport.model.Customer;
import com.tabcorp.saleReport.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public Flux<Customer> getAllCustomers() {
        return Flux.fromIterable(customerRepository.findAll());
    }

    public Mono<Customer> getCustomerById(Long id) {
        return Mono.justOrEmpty(customerRepository.findById(id));
    }

    /* Unused crud operation so commenting it out
    public Mono<Customer> saveCustomer(Customer customer) {
        return Mono.just(customerRepository.save(customer));
    }

    public Mono<Void> deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        return Mono.empty();
    }*/
}
