package com.repos;

import com.entities.Technician;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface TechnicianRepo extends CrudRepository<Technician, Integer> {
    Technician findTechnicianByUserName(String uname);
}
