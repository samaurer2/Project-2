package com.repos;

import com.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface ClientRepo extends CrudRepository<Client, Integer> {
    Client findClientByUserName(String uname);
}
