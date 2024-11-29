package com.oop.backend.repository;

import com.oop.backend.entity.Vendor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findByisSimulated(boolean isSimulated);
    Vendor findByEmail(String email);
}
