package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.dto.sale.CreateSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.GetSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.ListSaleDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Sale;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.SaleService;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    /* @GetMapping
    public List<Sale> getSales() {
        return saleService.findAll();
    } */

    @GetMapping
    public ResponseEntity<List<ListSaleDto>> getAllSales() {
        List<ListSaleDto> sales = saleService.getAllSales();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSaleDto> getSaleById(@PathVariable Long id) {

        Optional<GetSaleDto> saleDtoOptional = saleService.getSaleById(id);

        if (saleDtoOptional.isPresent()) {
            return ResponseEntity.ok(saleDtoOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody CreateSaleDto createSaleDto) {
        try {
            Sale sale = saleService.createSale(createSaleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(sale);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable Long id) {
        
        boolean deteledSale = saleService.deleteById(id);

        if (deteledSale) {
            return ResponseEntity.ok().body("Eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }

}
