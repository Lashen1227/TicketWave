package com.oop.backend.repository;

import com.oop.backend.entity.TicketPool;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
    TicketPool findByEventItemIdAndTicketsIsSoldFalse(Long eventItemId);

}