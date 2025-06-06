package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.universidad.tecno.api_gestion_accesorios.dto.InventoryReportItemDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.warehouse.GetWarehouseDetail;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;
import com.universidad.tecno.api_gestion_accesorios.entities.Warehouse;
import com.universidad.tecno.api_gestion_accesorios.entities.WarehouseDetail;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.WarehouseService;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseDetailRepository warehouseDetailRepository;

    @Override
    public Page<Warehouse> paginateAll(Pageable pageable) {
        return warehouseRepository.findAll(pageable);
    }

    @Override
    public List<Warehouse> findAll() {
        return (List<Warehouse>) warehouseRepository.findAll();
    }

    @Override
    public Optional<Warehouse> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public Optional<Warehouse> update(Long id, Warehouse warehouse) {
        return warehouseRepository.findById(id).map(existingWarehouse -> {
            if (warehouse.getName() != null)
                existingWarehouse.setName(warehouse.getName());
            if (warehouse.getAddress() != null)
                existingWarehouse.setAddress(warehouse.getAddress());
            return warehouseRepository.save(existingWarehouse);
        });
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Warehouse> warehouseOp = warehouseRepository.findById(id);
        if (warehouseOp.isPresent()) {
            warehouseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<GetWarehouseDetail> findAllWarehouseDetail() {
        List<WarehouseDetail> warehouseDetail = (List<WarehouseDetail>) warehouseDetailRepository.findAll();

        return warehouseDetail.stream().map(wd -> {
            GetWarehouseDetail dto = new GetWarehouseDetail();
            dto.setId(wd.getId());
            dto.setAccessoryId(wd.getAccessory().getId());
            dto.setAccessoryName(wd.getAccessory().getName());
            dto.setWarehouseId(wd.getWarehouse().getId());
            dto.setWarehouseName(wd.getWarehouse().getName());
            dto.setStock(wd.getStock());
            dto.setState(wd.getState());

            return dto;
        }).toList();
    }

    // ---------REPORTE--------------------

    @Override
    public List<InventoryReportItemDTO> getInventoryReport(List<Long> warehouseIds, List<Long> accessoryIds,
            List<Long> categoryIds) {
        // Convertir listas vacías en null para que el JPQL las ignore
        warehouseIds = (warehouseIds == null || warehouseIds.isEmpty()) ? null : warehouseIds;
        accessoryIds = (accessoryIds == null || accessoryIds.isEmpty()) ? null : accessoryIds;
        categoryIds = (categoryIds == null || categoryIds.isEmpty()) ? null : categoryIds;

        List<WarehouseDetail> details = warehouseDetailRepository.findWithFilters(warehouseIds, accessoryIds,
                categoryIds);

        return details.stream().map(wd -> {
            Accessory accessory = wd.getAccessory();
            BigDecimal price = BigDecimal.valueOf(accessory.getPrice());
            int stock = wd.getStock();
            BigDecimal totalValue = price.multiply(BigDecimal.valueOf(stock));

            return new InventoryReportItemDTO(
                    wd.getWarehouse().getName(),
                    accessory.getName(),
                    accessory.getCategory().getName(),
                    price,
                    stock,
                    totalValue);
        }).collect(Collectors.toList());
    }

    // -----------GENERADOR DE PDF----------------
    @Override
    public byte[] generateInventoryReportPdf(List<Long> warehouseIds, List<Long> accessoryIds, List<Long> categoryIds,
            String generatedBy) {
        List<InventoryReportItemDTO> items = getInventoryReport(warehouseIds, accessoryIds, categoryIds);

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("REPORTE DE INVENTARIO DETALLADO", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // Información general
            Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Paragraph info = new Paragraph("Generado por: " + generatedBy + "\nFecha: " + new java.util.Date(),
                    infoFont);
            info.setSpacingAfter(10f);
            document.add(info);

            // Tabla
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[] { 2.5f, 3, 2.5f, 2, 1.5f, 2.5f });

            addTableHeader(table);
            BigDecimal totalValue = BigDecimal.ZERO;
            int totalStock = 0;

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "BO"));

            for (InventoryReportItemDTO item : items) {
                table.addCell(item.getWarehouseName());
                table.addCell(item.getAccessoryName());
                table.addCell(item.getCategoryName());
                table.addCell(currencyFormat.format(item.getUnitPrice()));
                table.addCell(String.valueOf(item.getStock()));
                table.addCell(currencyFormat.format(item.getTotalValue()));

                totalValue = totalValue.add(item.getTotalValue());
                totalStock += item.getStock();
            }

            document.add(table);

            // Totales
            Paragraph totals = new Paragraph("Total de ítems: " + totalStock + " unidades\n" +
                    "Valor total del inventario: " + currencyFormat.format(totalValue), infoFont);
            totals.setSpacingBefore(15f);
            document.add(totals);

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar el PDF de inventario", e);
        }

        return out.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Almacén", "Accesorio", "Categoría", "Precio Unitario", "Stock", "Valor Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(columnTitle, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
                    table.addCell(header);
                });
    }

}
