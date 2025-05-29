package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.util.List;
import java.util.Optional;

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

import com.universidad.tecno.api_gestion_accesorios.dto.sale.CreateSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.GetSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.ListSaleDto;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.SaleService;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ListSaleDto>> listSalesPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<ListSaleDto> saleDtos = saleService.paginateAll(pageable);
        return ResponseEntity.ok(saleDtos);
    }

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
    public ResponseEntity<?> createSale(@RequestBody CreateSaleDto createSaleDto) {
        try {
            saleService.processSale(createSaleDto); // renombrado al nuevo método
            return ResponseEntity.status(HttpStatus.CREATED)
                    .build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable Long id) {

        boolean deteledSale = saleService.deleteById(id);

        if (deteledSale) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
