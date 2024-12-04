package com.oop.backend.repo;

import com.oop.backend.model.TicketPool;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TicketPoolRepo extends JpaRepository<TicketPool, Long> {
    TicketPool findByEventItemIdAndTicketsIsSoldFalse(Long eventItemId);

}