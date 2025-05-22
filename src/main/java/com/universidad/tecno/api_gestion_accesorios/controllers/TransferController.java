package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.dto.transfer.CreateTransferDto;
import com.universidad.tecno.api_gestion_accesorios.dto.transfer.ListTransferDto;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.TransferService;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ListTransferDto>> listarPaginado(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 7, Sort.by("date").descending());
        Page<ListTransferDto> transfers = transferService.paginateAll(pageable);
        return ResponseEntity.ok(transfers);
    }
    @GetMapping
    public ResponseEntity<List<ListTransferDto>> getAllTransfers() {
        List<ListTransferDto> transfers = transferService.findAll();
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransferById(@PathVariable Long id) {
        return transferService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<String> createTransfer(@RequestBody CreateTransferDto dto) {
        try {
            transferService.createTransfer(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransfer(@PathVariable Long id) {
        boolean deleted = transferService.deleteTransfer(id);
        if (deleted) {
            return ResponseEntity.ok().body("Eliminado correctamente");
        } 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontro la transferencia");

    }
}
