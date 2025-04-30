package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.purchase.CreatePurchaseDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.purchase.CreatePurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.dto.purchase.GetPurchaseDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.purchase.GetPurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.dto.purchase.ListPurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Purchase;
import com.universidad.tecno.api_gestion_accesorios.entities.PurchaseDetail;
import com.universidad.tecno.api_gestion_accesorios.entities.Supplier;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.WarehouseDetail;
import com.universidad.tecno.api_gestion_accesorios.repositories.PurchaseRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.SupplierRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.PurchaseService;

import jakarta.transaction.Transactional;

@Service
public class PurchaseImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WarehouseDetailRepository warehouseDetailRepository;

    @Override
    public List<Purchase> findAll() {
        return (List<Purchase>) purchaseRepository.findAll();
    }

    @Override
    public List<ListPurchaseDto> getAllPurchase() {
        List<Purchase> purchases = (List<Purchase>) purchaseRepository.findAll();

        return purchases.stream().map(p -> {
            ListPurchaseDto dto = new ListPurchaseDto();
            dto.setId(p.getId());
            dto.setTotalAmount(p.getTotalAmount());
            dto.setTotalQuantity(p.getTotalQuantity());
            dto.setPurchaseDate(p.getPurchaseDate());
            dto.setSupplierId(p.getSupplier().getId());
            dto.setUserId(p.getUser().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<GetPurchaseDto> getPurchaseById(Long id) {
        return purchaseRepository.findById(id).map(purchase -> {
            GetPurchaseDto dto = new GetPurchaseDto();
            dto.setId(purchase.getId());
            dto.setTotalAmount(purchase.getTotalAmount());
            dto.setTotalQuantity(purchase.getTotalQuantity());
            dto.setPurchaseDate(purchase.getPurchaseDate());
            dto.setSupplierName(purchase.getSupplier().getName());
            dto.setSupplierEmail(purchase.getSupplier().getEmail());
            dto.setUserName(purchase.getUser().getName());

            List<GetPurchaseDetailDto> details = purchase.getPurchaseDetails().stream().map(detail -> {
                GetPurchaseDetailDto detailDto = new GetPurchaseDetailDto();
                detailDto.setAccessoryId(detail.getWarehouseDetail().getAccessory().getId());
                detailDto.setAccessoryName(detail.getWarehouseDetail().getAccessory().getName());
                detailDto.setPrice(detail.getWarehouseDetail().getAccessory().getPrice());
                detailDto.setQuantityType(detail.getQuantityType());
                detailDto.setAmountType(detail.getAmountType());
                detailDto.setWarehouseName(detail.getWarehouseDetail().getWarehouse().getName());
                return detailDto;
            }).collect(Collectors.toList());

            dto.setPurchaseDetails(details);
            return dto;
        });
    }

    @Override
    @Transactional
    public Purchase createPurchase(CreatePurchaseDto purchaseDto) {
        // Validar proveedor
        if (purchaseDto.getSupplierId() == null) {
            throw new IllegalArgumentException("El ID del proveedor no puede ser nulo");
        }

        Supplier supplier = supplierRepository.findById(purchaseDto.getSupplierId())
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró al Proveedor con ID: " + purchaseDto.getSupplierId()));

        // Validar usuario
        if (purchaseDto.getUserId() == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }

        User user = userRepository.findById(purchaseDto.getUserId())
                .orElseThrow(
                        () -> new RuntimeException("No se encontró al Usuario con ID: " + purchaseDto.getUserId()));

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setSupplier(supplier);
        purchase.setUser(user);

        List<PurchaseDetail> purchaseDetails = new ArrayList<>();
        double totalAmount = 0.0;
        int totalQuantity = 0;

        for (CreatePurchaseDetailDto detailDto : purchaseDto.getPurchaseDetails()) {
            Long warehouseDetailId = detailDto.getWarehouseDetailId();
            if (warehouseDetailId == null) {
                throw new IllegalArgumentException("El ID de warehouseDetail no puede ser nulo");
            }

            WarehouseDetail warehouseDetail = warehouseDetailRepository.findById(warehouseDetailId)
                    .orElseThrow(
                            () -> new RuntimeException("No se encontró WarehouseDetail con ID: " + warehouseDetailId));

            int quantity = detailDto.getQuantityType();
            double amount = quantity * warehouseDetail.getAccessory().getPrice();

            PurchaseDetail detail = new PurchaseDetail();
            detail.setWarehouseDetail(warehouseDetail);
            detail.setQuantityType(quantity);
            detail.setAmountType(amount);
            detail.setPurchase(purchase);

            // Actualizar stock
            warehouseDetail.setStock(warehouseDetail.getStock() + quantity);

            purchaseDetails.add(detail);
            totalAmount += amount;
            totalQuantity += quantity;
        }

        purchase.setPurchaseDetails(purchaseDetails);
        purchase.setTotalAmount(totalAmount);
        purchase.setTotalQuantity(totalQuantity);

        return purchaseRepository.save(purchase);
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Purchase> existingPurchase = purchaseRepository.findById(id);
        if (existingPurchase.isPresent()) {
            purchaseRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
