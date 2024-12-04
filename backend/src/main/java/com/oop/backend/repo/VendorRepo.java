package com.oop.backend.repo;

import com.oop.backend.model.Vendor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface VendorRepo extends JpaRepository<Vendor, Long> {
    List<Vendor> findByisSimulated(boolean isSimulated);
    Vendor findByEmail(String email);
}
