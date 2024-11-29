package com.oop.backend.repository;

import com.oop.backend.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByisSimulated(boolean isSimulated);
    Customer findByEmail(String email);
}