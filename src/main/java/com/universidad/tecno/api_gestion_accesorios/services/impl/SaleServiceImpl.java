package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.sale.CreateSaleDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.CreateSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.GetSaleDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.GetSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.ListSaleDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;
import com.universidad.tecno.api_gestion_accesorios.entities.Sale;
import com.universidad.tecno.api_gestion_accesorios.entities.SaleDetail;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.WarehouseDetail;
import com.universidad.tecno.api_gestion_accesorios.repositories.AccessoryRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.SaleRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.SaleService;

import jakarta.transaction.Transactional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private WarehouseDetailRepository warehouseDetailRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Sale> findAll() {
        return (List<Sale>) saleRepository.findAll();
    }

    @Override
    public Page<ListSaleDto> paginateAll(Pageable pageable) {
        Page<Sale> salesPage = saleRepository.findAll(pageable);

        return salesPage.map(sale -> {
            ListSaleDto dto = new ListSaleDto();
            dto.setId(sale.getId());
            dto.setTotalAmount(sale.getTotalAmount());
            dto.setTotalQuantity(sale.getTotalQuantity());
            dto.setSaleDate(sale.getSaleDate());
            dto.setClientName(sale.getUser().getName());
            return dto;
        });
    }

    @Override
    public List<ListSaleDto> getAllSales() {
        List<Sale> sales = (List<Sale>) saleRepository.findAll();

        return sales.stream().map(sale -> {
            ListSaleDto dto = new ListSaleDto();
            dto.setId(sale.getId());
            dto.setTotalAmount(sale.getTotalAmount());
            dto.setTotalQuantity(sale.getTotalQuantity());
            dto.setSaleDate(sale.getSaleDate());
            dto.setClientName(sale.getUser().getName());
            return dto;
        }).toList();
    }

    @Override
    public Optional<GetSaleDto> getSaleById(Long id) {
        // Obtener la venta
        Optional<Sale> saleOptional = saleRepository.findById(id);

        if (!saleOptional.isPresent()) {
            return Optional.empty(); // Si no se encuentra la venta, devolver Optional vacío
        }

        Sale sale = saleOptional.get();

        // Obtener usuario
        User user = userRepository.findById(sale.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Mapear los detalles de la venta
        List<GetSaleDetailDto> saleDetails = sale.getSaleDetails().stream().map(saleDetail -> {
            // Obtener accesorio por ID
            Accessory accessory = accessoryRepository.findById(saleDetail.getWarehouseDetail().getAccessory().getId())
                    .orElseThrow(() -> new RuntimeException("Accesorio no encontrado"));

            // Crear SaleDetailDto
            GetSaleDetailDto saleDetailDto = new GetSaleDetailDto();
            saleDetailDto.setAccessoryId(accessory.getId());
            saleDetailDto.setAccessoryName(accessory.getName());
            saleDetailDto.setQuantityType(saleDetail.getQuantity());
            saleDetailDto.setAmountType(saleDetail.getAmountType());
            saleDetailDto.setPrice(accessory.getPrice());

            return saleDetailDto;
        }).collect(Collectors.toList());

        // Crear SaleDto
        GetSaleDto saleDto = new GetSaleDto();
        saleDto.setId(sale.getId());
        saleDto.setTotalAmount(sale.getTotalAmount());
        saleDto.setTotalQuantity(sale.getTotalQuantity());
        saleDto.setSaleDate(sale.getSaleDate());
        saleDto.setClientName(user.getName());
        saleDto.setClientEmail(user.getEmail());
        saleDto.setUserName(user.getName());
        saleDto.setSaleDetails(saleDetails);

        return Optional.of(saleDto); // Devolver el SaleDto envuelto en Optional
    }

    @Transactional
    public void processSale(CreateSaleDto dto) {

        double totalAmount = 0.0;
        int totalQuantity = 0;

        Sale sale = new Sale();
        sale.setUser(userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
                
        sale.setSaleDate(LocalDateTime.now());

        List<SaleDetail> saleDetails = new ArrayList<>();

        for (CreateSaleDetailDto item : dto.getSaleDetails()) {
            Long accessoryId = item.getAccessoryId();
            int quantity = item.getQuantity();

            // Obtener los almacenes con stock disponible
            List<WarehouseDetail> warehouseDetails = warehouseDetailRepository
                    .findByAccessoryIdAndStockGreaterThanAndStateOrderByStockDesc(
                            accessoryId, 0, "AVAILABLE");

            int remaining = quantity;

            for (WarehouseDetail detail : warehouseDetails) {
                if (remaining <= 0)
                    break;

                int available = detail.getStock();
                int toTake = Math.min(available, remaining);

                // Crear el detalle de venta
                SaleDetail saleDetail = new SaleDetail();
                saleDetail.setWarehouseDetail(detail); // aquí se setea correctamente
                saleDetail.setQuantity(toTake);
                saleDetail.setAmountType(detail.getAccessory().getPrice() * toTake); // calculas el subtotal
                saleDetail.setSale(sale);

                saleDetails.add(saleDetail);

                // Actualizar stock
                detail.setStock(available - toTake);
                warehouseDetailRepository.save(detail);

                remaining -= toTake;
            }

            if (remaining > 0) {
                throw new RuntimeException("Stock insuficiente para el accesorio con ID: " + accessoryId);
            }
        }
        for (SaleDetail detail : saleDetails) {
            totalAmount += detail.getAmountType(); // amountType ya es precio * cantidad
            totalQuantity += detail.getQuantity();
        }

        // solo redondeo a 2 decimales
        BigDecimal totalRounded = BigDecimal.valueOf(totalAmount).setScale(2, RoundingMode.HALF_UP);
        sale.setTotalAmount(totalRounded.doubleValue());

        // sale.setTotalAmount(totalAmount);
        sale.setTotalQuantity(totalQuantity);
        sale.setSaleDetails(saleDetails);
        saleRepository.save(sale); // Cascade puede guardar saleDetails también

        // Opcional: devolver ID o resumen
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Sale> exsitingSale = saleRepository.findById(id);

        if (exsitingSale.isPresent()) {
            saleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ------------------REPORTES-------------------
    @Override
    public List<Sale> getSalesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository.findBySaleDateBetween(startDate, endDate);
    }

    //---------------- generador de pdf----------------------
    @Override
    public byte[] generateSalesReportBetweenDates(LocalDateTime startDate, LocalDateTime endDate)
            throws DocumentException {
        List<Sale> sales = saleRepository.findBySaleDateBetween(startDate, endDate);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        Paragraph title = new Paragraph("Reporte Detallado de Ventas", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Sale sale : sales) {
            Paragraph saleHeader = new Paragraph(
                    "Venta ID: " + sale.getId() + " | Fecha: " + formatter.format(sale.getSaleDate()) +
                            " | Usuario: " + sale.getUser().getUsername(),
                    subTitleFont);
            saleHeader.setSpacingBefore(10);
            saleHeader.setSpacingAfter(5);
            document.add(saleHeader);

            PdfPTable detailTable = new PdfPTable(5);
            detailTable.setWidthPercentage(100);
            detailTable.setWidths(new float[] { 4, 3, 2, 2, 2 });

            Stream.of("Accesorio", "Marca/Modelo", "Cantidad", "Precio Unitario", "Subtotal")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell(new Phrase(header, subTitleFont));
                        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        detailTable.addCell(cell);
                    });

            for (SaleDetail detail : sale.getSaleDetails()) {
                String accessoryName = detail.getWarehouseDetail().getAccessory().getName();
                String brandModel = detail.getWarehouseDetail().getAccessory().getBrand() + " " +
                        detail.getWarehouseDetail().getAccessory().getModel();
                Integer quantity = detail.getQuantity();
                Double priceUnit = detail.getAmountType();
                Double subtotal = priceUnit * quantity;

                detailTable.addCell(new PdfPCell(new Phrase(accessoryName, normalFont)));
                detailTable.addCell(new PdfPCell(new Phrase(brandModel, normalFont)));
                detailTable.addCell(new PdfPCell(new Phrase(quantity.toString(), normalFont)));
                detailTable.addCell(new PdfPCell(new Phrase(String.format("%.2f Bs", priceUnit), normalFont)));
                detailTable.addCell(new PdfPCell(new Phrase(String.format("%.2f Bs", subtotal), normalFont)));
            }

            document.add(detailTable);

            Paragraph totals = new Paragraph(
                    String.format("Cantidad Total: %d    Monto Total: %.2f Bs",
                            sale.getTotalQuantity(), sale.getTotalAmount()),
                    subTitleFont);
            totals.setSpacingBefore(5);
            totals.setAlignment(Element.ALIGN_RIGHT);
            document.add(totals);

            document.add(new Paragraph(" "));
        }

        document.close();
        return out.toByteArray();
    }

}
