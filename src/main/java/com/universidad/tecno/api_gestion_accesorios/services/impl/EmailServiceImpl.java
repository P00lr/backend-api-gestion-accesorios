package com.universidad.tecno.api_gestion_accesorios.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.services.interfaces.EmailService;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.WarehouseService;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void enviarCorreoConAdjunto(
            String destinatarios,
            String asunto,
            String mensaje,
            byte[] pdfBytes,
            String nombreArchivo) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        String[] destinatariosArray = destinatarios.split("\\s*,\\s*"); // divide por coma y elimina espacios

        helper.setTo(destinatariosArray);
        helper.setSubject(asunto);
        helper.setText(mensaje, true); // true si quieres HTML

        InputStreamSource archivo = new ByteArrayResource(pdfBytes);
        helper.addAttachment(nombreArchivo, archivo);

        mailSender.send(mimeMessage);
    }

    @Override
    public void enviarReporteInventario(
            List<String> destinatarios,
            List<Long> warehouseIds,
            List<Long> accessoryIds,
            List<Long> categoryIds,
            String generadoPor,
            WarehouseService warehouseService) throws MessagingException {

        byte[] pdf = warehouseService.generateInventoryReportPdf(warehouseIds, accessoryIds, categoryIds, generadoPor);
        String asunto = "Reporte de Inventario Detallado";
        String mensaje = "<p>Adjunto encontrarás el reporte de inventario solicitado.</p>";

        for (String destinatario : destinatarios) {
            enviarCorreoConAdjunto(destinatario, asunto, mensaje, pdf, "ReporteInventario.pdf");
        }
    }

}
