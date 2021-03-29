package com.app.repotests;

import com.entities.Admin;
import com.entities.Technician;
import com.repos.TechnicianRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TechRepoTests {

    @Autowired
    TechnicianRepo technicianRepo;

    @Test
    void findTechTest(){
        Technician technician = technicianRepo.findTechnicianByUserName("technician");
        Assertions.assertTrue(!(technician instanceof Admin));
    }

    @Test
    void findAdminTest(){
        Technician admin = technicianRepo.findTechnicianByUserName("admin");
        Assertions.assertTrue(admin instanceof Admin);
    }
}
