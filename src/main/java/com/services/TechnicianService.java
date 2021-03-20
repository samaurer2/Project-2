package com.services;

import com.entities.Technician;

public interface TechnicianService {

    //READ
    Technician getTech(String uname);

    Technician getTechnicianById(int techId);
    List<Technician> getAllTechnicians();

}
