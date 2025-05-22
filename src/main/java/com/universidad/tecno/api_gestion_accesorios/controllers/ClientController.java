package com.universidad.tecno.api_gestion_accesorios.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.entities.Client;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.ClientService;

@CrossOrigin(origins={"http://localhost:4500"})
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Client> clients = clientService.paginateAll(pageable);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        return clientService.findById(id)
            .map(client -> ResponseEntity.ok(client))
            .orElseGet( ()-> ResponseEntity.notFound().build());
    }   

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client newClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.update(id, client)
            .map(updatedClient -> ResponseEntity.ok().body(updatedClient))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        boolean deleted = clientService.deleteById(id);
        if(deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
     }
}
