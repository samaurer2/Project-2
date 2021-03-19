package com.app.servicetests;

import com.entities.Client;
import com.entities.Technician;
import com.repos.ClientRepo;
import com.repos.TechnicianRepo;
import com.services.ClientService;
import com.services.ClientServiceImpl;
import com.services.TechnicianService;
import com.services.TechnicianServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TechnicianServiceTest {

    TechnicianService technicianService;

    @Mock
    TechnicianRepo technicianRepo = Mockito.mock(TechnicianRepo.class);

    @BeforeEach
    void setup(){
        technicianService = new TechnicianServiceImpl(technicianRepo);
        Mockito.when(technicianService.getTech(Mockito.anyString())).thenReturn(new Technician("Mr. Admin", "mockito"));
    }



    @Test
    public void get_client(){

        Technician technician = this.technicianService.getTech("Mr. Admin");
        Assertions.assertEquals("Mr. Admin", technician.getUserName());
        System.out.println(technician);
    }
}
