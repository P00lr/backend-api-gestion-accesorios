package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.dto.purchase.CreatePurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.dto.purchase.GetPurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.dto.purchase.ListPurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Purchase;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.PurchaseService;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    /*
     * @GetMapping
     * public List<Purchase> getPurchases() {
     * return (List<Purchase>) purchaseService.findAll();
     * }
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetPurchaseDto> getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ListPurchaseDto>> getAllPurchases() {
        List<ListPurchaseDto> purchases = purchaseService.getAllPurchase();
        return ResponseEntity.ok(purchases);
    }

    @PostMapping
    public ResponseEntity<String> createPurchase(@RequestBody CreatePurchaseDto dto) {
        try {
            purchaseService.createPurchase(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Compra registrada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar la compra: " + e.getMessage());
        }
    }
    
}
