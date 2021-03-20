package com.services;

import com.entities.Technician;

import java.util.List;

public interface TechnicianService {

    //READ
    Technician getTech(String uname);

    Technician getTechnicianById(int techId);
    List<Technician> getAllTechnicians();

}
