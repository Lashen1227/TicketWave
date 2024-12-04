package com.oop.backend.repo;

import com.oop.backend.model.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TicketRepo extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEventItemId(Long eventItemId);
}
