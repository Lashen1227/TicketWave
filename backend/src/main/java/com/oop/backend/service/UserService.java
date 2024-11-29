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

    public String generateSimpleToken(Long userId) {
        // Simple token generation (not secure, just for demo purposes)
        return Base64.getEncoder().encodeToString((userId + ":" + System.currentTimeMillis()).getBytes());
    }
}
