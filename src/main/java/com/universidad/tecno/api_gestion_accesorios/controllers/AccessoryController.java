package com.universidad.tecno.api_gestion_accesorios.controllers;

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

import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryAssignmentCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryWithCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.AccessoryService;

@RestController
@RequestMapping("/api/accessories")
public class AccessoryController {

    @Autowired
    private AccessoryService accessoryService;

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<AccessoryWithCategoryDto>> listarPaginado(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 7);
        Page<AccessoryWithCategoryDto> accesorios = accessoryService.paginarTodo(pageable);
        return ResponseEntity.ok(accesorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessoryWithCategoryDto> getAccessory(@PathVariable Long id) {
        return accessoryService.findById(id)
                .map(accessory -> ResponseEntity.ok(accessory))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
     * @PostMapping
     * public ResponseEntity<Accessory> createAccessory(@RequestBody Accessory
     * accessory) {
     * Accessory newAccessory = accessoryService.save(accessory);
     * return ResponseEntity.status(HttpStatus.CREATED).body(newAccessory);
     * }
     */

    @PostMapping
    public ResponseEntity<Accessory> createAccessory(@RequestBody AccessoryAssignmentCategoryDto dto) {
        Accessory accessory = accessoryService.saveDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(accessory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessoryWithCategoryDto> updateAccessory(
            @PathVariable Long id,
            @RequestBody AccessoryAssignmentCategoryDto dto) {

        return accessoryService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Accessory> deleteAccessory(@PathVariable Long id) {
        boolean deleted = accessoryService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
