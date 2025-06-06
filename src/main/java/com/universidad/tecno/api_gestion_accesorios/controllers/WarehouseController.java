package com.universidad.tecno.api_gestion_accesorios.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.dto.warehouse.GetWarehouseDetail;
import com.universidad.tecno.api_gestion_accesorios.entities.Warehouse;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.WarehouseService;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Warehouse> warehouses = warehouseService.paginateAll(pageable);
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping
    public List<Warehouse> getWarehouses() {
        return warehouseService.findAll();
    }

    @GetMapping("/details")
    public List<GetWarehouseDetail> getWarehouseDetails() {
        return warehouseService.findAllWarehouseDetail();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouse(@PathVariable Long id) {
        return warehouseService.findById(id)
                .map(warehouse -> ResponseEntity.ok(warehouse))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse newWarehouse = warehouseService.save(warehouse);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWarehouse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, @RequestBody Warehouse warehouse) {
        return warehouseService.update(id, warehouse)
                .map(updatedWarehouse -> ResponseEntity.ok(updatedWarehouse))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Warehouse> deleteWarehouse(@PathVariable Long id) {
        boolean deleted = warehouseService.deleteById(id);
        if (deleted)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    // -----------REPORTE------------------------

    @GetMapping("/report")
    public ResponseEntity<byte[]> downloadInventoryReport(
            @RequestParam(required = false) List<Long> warehouseIds,
            @RequestParam(required = false) List<Long> accessoryIds,
            @RequestParam(required = false) List<Long> categoryIds,
            Authentication authentication) {

        // Obtener el username (email o nombre de usuario)
        String username = authentication.getName();

        // Buscar el email real del usuario
        String email = userRepository.findByUsername(username)
                .map(u -> u.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generar el PDF del reporte
        byte[] pdf = warehouseService.generateInventoryReportPdf(warehouseIds, accessoryIds, categoryIds, email);

        // Configurar headers de respuesta
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "inventario.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

}
