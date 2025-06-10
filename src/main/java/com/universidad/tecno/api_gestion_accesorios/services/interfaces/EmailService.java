package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;

import jakarta.mail.MessagingException;

public interface EmailService {
    public void enviarCorreoConAdjunto(
            String to,
            String subject,
            String text,
            byte[] attachment,
            String fileName) throws MessagingException;

    public void enviarReporteInventario(
            List<String> destinatarios,
            List<Long> warehouseIds,
            List<Long> accessoryIds,
            List<Long> categoryIds,
            String generadoPor,
            String asunto,
            String mensaje,
            WarehouseService warehouseService) throws MessagingException;     

}