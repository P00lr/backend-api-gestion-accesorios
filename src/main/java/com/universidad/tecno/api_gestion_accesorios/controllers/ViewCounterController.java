package com.universidad.tecno.api_gestion_accesorios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.services.interfaces.ViewCounterService;

@CrossOrigin(origins = "https://inspiring-mermaid-ca6cdd.netlify.app")
@RestController
@RequestMapping("api/views")
public class ViewCounterController {

    @Autowired
    private ViewCounterService viewCounterService;

    @PostMapping("/{page}")
    public ResponseEntity<Long> countView(@PathVariable String page) {
        Long updatedCount = viewCounterService.incrementView(page);
        return ResponseEntity.ok(updatedCount);
    }

    @GetMapping("/{page}")
    public ResponseEntity<Long> getViews(@PathVariable String page) {
        return ResponseEntity.ok(viewCounterService.getViews(page));
    }
}
