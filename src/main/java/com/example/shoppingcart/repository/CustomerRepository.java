package com.example.shoppingcart.repository;

import com.example.shoppingcart.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer getCustomerByEmailAndName(String email,String name);
    Optional<Customer>  getCustomersByEmail(String email);

}
