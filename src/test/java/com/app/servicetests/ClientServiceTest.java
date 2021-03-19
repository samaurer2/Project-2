package com.app.servicetests;


import com.entities.Client;
import com.repos.ClientRepo;
import com.services.ClientService;
import com.services.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ClientServiceTest {


    ClientService clientService;

    @Mock
    ClientRepo clientRepo = Mockito.mock(ClientRepo.class);

    @BeforeEach
    void setup(){
        clientService = new ClientServiceImpl(clientRepo);
        Mockito.when(clientService.getClient(Mockito.anyString())).thenReturn(new Client("Mr. Mock", "mockito"));
    }



    @Test
    public void get_client(){

        Client client = this.clientService.getClient("Mr. Mock");
        Assertions.assertEquals("Mr. Mock", client.getUserName());
        System.out.println(client);
    }

}
