package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.entities.Client;

public interface ClientService {

    List<Client> findAll();
    Optional<Client> findById(Long id);
    Optional<Client> update(Long id, Client client);
    Client save(Client client);
    boolean deleteById(Long id); 
}
