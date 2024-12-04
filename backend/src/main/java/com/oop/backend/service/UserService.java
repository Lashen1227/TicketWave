package com.oop.backend.service;

import com.oop.backend.model.Customer;
import com.oop.backend.model.Vendor;
import com.oop.backend.repo.CustomerRepo;
import com.oop.backend.repo.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private CustomerRepo customerRepository;
    @Autowired
    private VendorRepo vendorRepository;

    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Vendor findVendorByEmail(String email) {
        return vendorRepository.findByEmail(email);
    }

    public boolean emailExists(String email) {
        return customerRepository.findByEmail(email) != null || vendorRepository.findByEmail(email) != null;
    }

    // Generate a simple token for the user
    public String generateSimpleToken(Long userId) {
        return Base64.getEncoder().encodeToString((userId + ":" + System.currentTimeMillis()).getBytes());
    }
}
