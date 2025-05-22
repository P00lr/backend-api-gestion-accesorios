package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.entities.Client;
import com.universidad.tecno.api_gestion_accesorios.repositories.ClientRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.ClientService;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

     @Override
    public Page<Client> paginateAll(Pageable pageable) {
        return this.clientRepository.findAll(pageable);
    }

    @Override
    public List<Client> findAll() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> update(Long id, Client client) {
        return clientRepository.findById(id).map(existingClient -> {
            if (client.getName() != null)
                existingClient.setName(client.getName());
            if (client.getUsername() != null)
                existingClient.setUsername(client.getUsername());
            if (client.getPassword() != null)
                existingClient.setPassword(client.getPassword());
            if (client.getEmail() != null)
                existingClient.setEmail(client.getEmail());
            if (client.getFechaNacimiento() != null)
                existingClient.setFechaNacimiento(client.getFechaNacimiento());
            return clientRepository.save(existingClient);
        });
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Client> clientOp = clientRepository.findById(id);
        if (clientOp.isPresent()) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

   
}
