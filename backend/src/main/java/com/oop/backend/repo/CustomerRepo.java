package com.oop.backend.repo;

import com.oop.backend.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    List<Customer> findByisSimulated(boolean isSimulated);
    Customer findByEmail(String email);
}