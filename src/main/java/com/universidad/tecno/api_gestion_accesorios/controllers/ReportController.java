package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.EmailService;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.SaleService;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.WarehouseService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("api/email")
public class ReportController {
    
    @Autowired
    private SaleService saleService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/sale")
    public String enviarReporteVentasPorCorreo(
            @RequestParam String destinatario,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        try {
            byte[] pdf = saleService.generateSalesReportBetweenDates(inicio, fin);

            String asunto = "Reporte de ventas del " + inicio.toLocalDate() + " al " + fin.toLocalDate();
            String mensaje = "Adjunto el reporte de ventas solicitado.";

            emailService.enviarCorreoConAdjunto(
                    destinatario,
                    asunto,
                    mensaje,
                    pdf,
                    "ReporteVentas.pdf");

            return "Correo enviado correctamente a " + destinatario;
        } catch (DocumentException e) {
            e.printStackTrace();
            return "Error al generar el PDF: " + e.getMessage();
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
    }

    //-----------------------Inventario-----------------
    @Autowired
    private WarehouseService warehouseService;

    // DTO para recibir la petición
    public static class InventoryReportRequest {
        public List<String> destinatarios;
        public List<Long> warehouseIds;
        public List<Long> accessoryIds;
        public List<Long> categoryIds;
        public String generadoPor;
    }

    @PostMapping("/inventory")
    public ResponseEntity<String> sendInventoryReport(@RequestBody InventoryReportRequest request) {
        try {
            emailService.enviarReporteInventario(
                    request.destinatarios,
                    request.warehouseIds,
                    request.accessoryIds,
                    request.categoryIds,
                    request.generadoPor,
                    warehouseService
            );
            return ResponseEntity.ok("Reporte de inventario enviado correctamente");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error enviando el correo: " + e.getMessage());
        }
    }
}
