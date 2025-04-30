package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.util.List;

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

import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.CreateAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.GetAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.ListAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.AdjustmentService;

@RestController
@RequestMapping("/api/adjustments")
public class AdjustmentController {

    @Autowired
    private AdjustmentService adjustmentService;

    @GetMapping
    public ResponseEntity<List<ListAdjustmentDto>> getAllAdjustments() {
        return ResponseEntity.ok(adjustmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAdjustmentDto> getAdjustmentById(@PathVariable Long id) {
        return ResponseEntity.ok(adjustmentService.getAdjustmentById(id));
    }

    @PostMapping
    public ResponseEntity<String> createAdjustment(@RequestBody CreateAdjustmentDto dto) {
        try {
            adjustmentService.createAdjustment(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ajuste registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar ajuste: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdjustment(@PathVariable Long id) {
        boolean deleted = adjustmentService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().body("Eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar");
    }
}
