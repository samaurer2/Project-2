package com.repos;

import com.entities.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface TicketRepo extends CrudRepository<Ticket, Integer> {

    List<Ticket> findAllByClientIdEquals(Integer clientId);
}
