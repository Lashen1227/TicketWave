package com.oop.backend.service;

import com.oop.backend.entity.Customer;
import com.oop.backend.entity.Vendor;
import com.oop.backend.repository.CustomerRepository;
import com.oop.backend.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;

    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Vendor findVendorByEmail(String email) {
        return vendorRepository.findByEmail(email);
    }

    public boolean emailExists(String email) {
        return customerRepository.findByEmail(email) != null || vendorRepository.findByEmail(email) != null;
    }

    public String generateSimpleToken(Long userId) {
        // Generate a simple token for the user
        return Base64.getEncoder().encodeToString((userId + ":" + System.currentTimeMillis()).getBytes());
    }
}
