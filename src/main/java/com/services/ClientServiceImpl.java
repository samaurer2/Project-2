package com.services;

import com.entities.Client;
import com.repos.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    ClientRepo clientRepo;

    public ClientServiceImpl(){

    }
    public ClientServiceImpl(ClientRepo clientRepo){
        this.clientRepo = clientRepo;
    }
    @Override
    public Client getClient(String uname) {
        return this.clientRepo.findClientByUserName(uname);
    }
}
