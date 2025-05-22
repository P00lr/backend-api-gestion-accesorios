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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.dto.purchase.CreatePurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.dto.purchase.GetPurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.dto.purchase.ListPurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.PurchaseService;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ListPurchaseDto>> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<ListPurchaseDto> purchaseDtos = purchaseService.paginateAll(pageable);
        return ResponseEntity.ok(purchaseDtos);
    }

    @GetMapping
    public ResponseEntity<List<ListPurchaseDto>> getAllPurchases() {
        List<ListPurchaseDto> purchases = purchaseService.getAllPurchase();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPurchaseDto> getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createPurchase(@RequestBody CreatePurchaseDto dto) {
        try {
            purchaseService.createPurchase(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        boolean deleted = purchaseService.deleteById(id);

        if (deleted) {
            return ResponseEntity.ok().build(); 
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
