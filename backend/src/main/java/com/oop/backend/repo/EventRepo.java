package com.oop.backend.repo;

import com.oop.backend.model.EventItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface EventRepo extends JpaRepository<EventItem, Long> {
    List<EventItem> findByVendorId(Long vendorId);
    List<EventItem> findByisSimulated(boolean b);
}
