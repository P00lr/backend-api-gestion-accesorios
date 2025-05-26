package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
import com.universidad.tecno.api_gestion_accesorios.entities.Client;
import com.universidad.tecno.api_gestion_accesorios.entities.Sale;
import com.universidad.tecno.api_gestion_accesorios.entities.SaleDetail;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.WarehouseDetail;
import com.universidad.tecno.api_gestion_accesorios.repositories.AccessoryRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.ClientRepository;
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
    private ClientRepository clientRepository;

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
            dto.setClientId(sale.getClient().getId());
            dto.setUserId(sale.getUser().getId());
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
            dto.setClientId(sale.getClient().getId());
            dto.setUserId(sale.getUser().getId());
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

        // Obtener cliente y usuario
        Client client = clientRepository.findById(sale.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
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
        saleDto.setClientName(client.getName());
        saleDto.setClientEmail(client.getEmail());
        saleDto.setUserName(user.getName());
        saleDto.setSaleDetails(saleDetails);

        return Optional.of(saleDto); // Devolver el SaleDto envuelto en Optional
    }

    @Transactional
    public void processSale(CreateSaleDto dto) {

        double totalAmount = 0.0;
        int totalQuantity = 0;

        Sale sale = new Sale();
        sale.setClient(clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
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

        //solo redondeo a 2 decimales
        BigDecimal totalRounded = BigDecimal.valueOf(totalAmount).setScale(2, RoundingMode.HALF_UP);
        sale.setTotalAmount(totalRounded.doubleValue());

        //sale.setTotalAmount(totalAmount);
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

}
