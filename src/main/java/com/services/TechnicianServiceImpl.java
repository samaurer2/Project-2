package com.services;

import com.entities.Technician;
import com.repos.TechnicianRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class TechnicianServiceImpl implements TechnicianService{

    @Autowired
    TechnicianRepo technicianRepo;

    public TechnicianServiceImpl(){}
    public TechnicianServiceImpl(TechnicianRepo technicianRepo){
        this.technicianRepo = technicianRepo;
    }
    @Override
    public Technician getTech(String uname) {
        return this.technicianRepo.findTechnicianByUserName(uname);
    }
}
