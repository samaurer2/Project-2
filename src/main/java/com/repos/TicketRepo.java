package com.repos;

import com.entities.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface TicketRepo extends CrudRepository<Ticket, Integer> {
}
