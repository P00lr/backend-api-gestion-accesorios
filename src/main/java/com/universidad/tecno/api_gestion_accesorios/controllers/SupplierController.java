package com.universidad.tecno.api_gestion_accesorios.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.entities.Supplier;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.SupplierService;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Supplier> suppliers = supplierService.paginateAll(pageable);
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping
    public List<Supplier> listSuppliers() {
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable Long id) {
        return supplierService.findById(id)
        .map( supplier -> ResponseEntity.ok(supplier)
        ).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        Supplier newSupplier = supplierService.save(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSupplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        return supplierService.update(id, supplier)
            .map(updateSupplier -> ResponseEntity.ok(updateSupplier))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        boolean deletedSupplier = supplierService.deleteById(id);
        if(deletedSupplier) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar");

    } 


}
