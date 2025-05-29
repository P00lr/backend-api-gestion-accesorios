package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.CreateAdjustmentDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.CreateAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.GetAdjustmentDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.GetAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.ListAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;
import com.universidad.tecno.api_gestion_accesorios.entities.Adjustment;
import com.universidad.tecno.api_gestion_accesorios.entities.AdjustmentDetail;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.Warehouse;
import com.universidad.tecno.api_gestion_accesorios.entities.WarehouseDetail;
import com.universidad.tecno.api_gestion_accesorios.repositories.AccessoryRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.AdjustmentDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.AdjustmentRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.AdjustmentService;

import jakarta.transaction.Transactional;

@Service
public class AdjustmentServiceImpl implements AdjustmentService {

    @Autowired
    private AdjustmentRepository adjustmentRepository;

    @Autowired
    private AdjustmentDetailRepository adjustmentDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WarehouseDetailRepository warehouseDetailRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Override
    public Page<ListAdjustmentDto> paginateAll(Pageable pageable) {
        Page<Adjustment> adjustmentsPage = adjustmentRepository.findAll(pageable);

        return adjustmentsPage.map(adjustment -> {
            int totalQuantity = adjustment.getAdjustmentDetails()
                    .stream()
                    .mapToInt(AdjustmentDetail::getQuantity)
                    .sum();

            return new ListAdjustmentDto(
                    adjustment.getId(),
                    adjustment.getDate(),
                    adjustment.getType(),
                    adjustment.getDescription(),
                    totalQuantity);
        });
    }

    @Override
    public List<ListAdjustmentDto> findAll() {
        List<Adjustment> adjustments = (List<Adjustment>) adjustmentRepository.findAll();

        return adjustments.stream().map(adjustment -> {
            int totalQuantity = adjustment.getAdjustmentDetails()
                    .stream()
                    .mapToInt(AdjustmentDetail::getQuantity)
                    .sum();

            return new ListAdjustmentDto(
                    adjustment.getId(),
                    adjustment.getDate(),
                    adjustment.getType(),
                    adjustment.getDescription(),
                    totalQuantity);
        }).collect(Collectors.toList());
    }

    @Override
    public GetAdjustmentDto getAdjustmentById(Long id) {
        Adjustment adjustment = adjustmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ajuste no encontrado"));

        List<GetAdjustmentDetailDto> details = adjustment.getAdjustmentDetails().stream()
                .map(detail -> new GetAdjustmentDetailDto(
                        detail.getWarehouseDetail().getAccessory().getName(),
                        detail.getQuantity(),
                        detail.getItemDescription()))
                .collect(Collectors.toList());

        String userFullName = adjustment.getUser().getName();

        // Obtener el nombre del almacén desde el primer detalle
        String warehouseName = adjustment.getAdjustmentDetails().isEmpty()
                ? "N/A"
                : adjustment.getAdjustmentDetails().get(0).getWarehouseDetail().getWarehouse().getName();

        return new GetAdjustmentDto(
                adjustment.getId(),
                adjustment.getDate(),
                adjustment.getType(),
                adjustment.getDescription(),
                userFullName,
                warehouseName,
                details);
    }

    @Override
    @Transactional
    public Adjustment createAdjustment(CreateAdjustmentDto dto) {
        // 1. Obtener el almacén
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + dto.getWarehouseId()));

        // 2. Obtener el usuario
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUserId()));

        // 3. Crear el ajuste
        Adjustment adjustment = new Adjustment();
        adjustment.setType(dto.getType().toUpperCase());
        adjustment.setDate(dto.getDate());
        adjustment.setDescription(dto.getDescription());
        adjustment.setUser(user);
        adjustment = adjustmentRepository.save(adjustment); // Guardar para generar ID

        // 4. Procesar cada detalle
        for (CreateAdjustmentDetailDto detailDto : dto.getAdjustmentDetails()) {
            // Obtener accesorio
            Accessory accessory = accessoryRepository.findById(detailDto.getAccessoryId())
                    .orElseThrow(() -> new RuntimeException(
                            "Accesorio no encontrado con ID: " + detailDto.getAccessoryId()));

            // Buscar o crear WarehouseDetail
            WarehouseDetail warehouseDetail = warehouseDetailRepository
                    .findByWarehouseIdAndAccessoryId(warehouse.getId(), accessory.getId())
                    .orElseGet(() -> {
                        WarehouseDetail newDetail = new WarehouseDetail();
                        newDetail.setWarehouse(warehouse);
                        newDetail.setAccessory(accessory);
                        newDetail.setStock(0); // Inicializar con stock 0
                        newDetail.setState("AVAILABLE");
                        return newDetail;
                    });

            int quantity = detailDto.getQuantity();

            // Ajustar el stock según el tipo de ajuste
            switch (dto.getType().toUpperCase()) {
                case "INGRESO":
                    warehouseDetail.setStock(warehouseDetail.getStock() + quantity);
                    break;
                case "EGRESO":
                    if (warehouseDetail.getStock() < quantity) {
                        throw new IllegalArgumentException(
                                "Stock insuficiente para egreso del accesorio: " + accessory.getName());
                    }
                    warehouseDetail.setStock(warehouseDetail.getStock() - quantity);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de ajuste no válido: " + dto.getType());
            }

            // Guardar el WarehouseDetail (por si fue creado o modificado)
            warehouseDetail = warehouseDetailRepository.save(warehouseDetail);

            // Crear y guardar el detalle del ajuste
            AdjustmentDetail adjustmentDetail = new AdjustmentDetail();
            adjustmentDetail.setAdjustment(adjustment);
            adjustmentDetail.setWarehouseDetail(warehouseDetail);
            adjustmentDetail.setQuantity(quantity);
            adjustmentDetail.setItemDescription(detailDto.getItemDescription()); // Solo si

            adjustmentDetailRepository.save(adjustmentDetail);
        }

        return adjustment;
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Adjustment> existingAdjustment = adjustmentRepository.findById(id);

        if (existingAdjustment.isPresent()) {
            adjustmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
