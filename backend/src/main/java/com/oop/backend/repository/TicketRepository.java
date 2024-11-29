package com.oop.backend.repository;

import com.oop.backend.entity.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEventItemId(Long eventItemId);
}
