package com.repos;

import com.entities.TechTickPK;
import com.entities.TechTicket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface TechTicketRepo extends CrudRepository<TechTicket, TechTickPK> {

    @Query("FROM TechTicket t WHERE t.pk.techId = :techId")
    public List<TechTicket> findAllByTechId(int techId);

    @Query("FROM TechTicket t WHERE t.pk.ticketId = :ticketId")
    public List<TechTicket> findAllByTicketId(int ticketId);
}
