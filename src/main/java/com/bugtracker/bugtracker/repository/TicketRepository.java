package com.bugtracker.bugtracker.repository;

import com.bugtracker.bugtracker.entity.Ticket;
import com.bugtracker.bugtracker.entity.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer>{

    Optional<Ticket> findById(int ticketId);

    int countDistinctTicketByType(Type type);

}
