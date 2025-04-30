package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.CreateAdjustmentDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.CreateAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.GetAdjustmentDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.GetAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.ListAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Adjustment;
import com.universidad.tecno.api_gestion_accesorios.entities.AdjustmentDetail;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.WarehouseDetail;
import com.universidad.tecno.api_gestion_accesorios.repositories.AdjustmentDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.AdjustmentRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseDetailRepository;
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
                .orElseThrow(() -> new RuntimeException("Adjustment not found"));

        List<GetAdjustmentDetailDto> details = adjustment.getAdjustmentDetails().stream()
                .map(detail -> new GetAdjustmentDetailDto(
                        detail.getWarehouseDetail().getId(),
                        detail.getWarehouseDetail().getAccessory().getName(),
                        detail.getWarehouseDetail().getWarehouse().getName(),
                        detail.getQuantity()))
                .collect(Collectors.toList());

        String userFullName = adjustment.getUser().getName();

        return new GetAdjustmentDto(
                adjustment.getId(),
                adjustment.getDate(),
                adjustment.getType(),
                adjustment.getDescription(),
                userFullName,
                details);
    }

    @Override
    @Transactional
    public Adjustment createAdjustment(CreateAdjustmentDto dto) {
        // Buscar el usuario
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        // Crear el ajuste
        Adjustment adjustment = new Adjustment();
        adjustment.setDate(dto.getDate());
        adjustment.setType(dto.getType());
        adjustment.setDescription(dto.getDescription());
        adjustment.setUser(user);

        // Guardar el ajuste
        adjustment = adjustmentRepository.save(adjustment);

        // Crear y asociar los detalles del ajuste
        for (CreateAdjustmentDetailDto detailDto : dto.getAdjustmentDetails()) {
            WarehouseDetail warehouseDetail = warehouseDetailRepository.findById(detailDto.getWarehouseDetailId())
                    .orElseThrow(() -> new RuntimeException("Warehouse detail not found"));

            AdjustmentDetail adjustmentDetail = new AdjustmentDetail();
            adjustmentDetail.setAdjustment(adjustment);
            adjustmentDetail.setWarehouseDetail(warehouseDetail);
            adjustmentDetail.setQuantity(detailDto.getQuantity());

            adjustmentDetailRepository.save(adjustmentDetail);

            // Actualizar el stock dependiendo del tipo de ajuste
            if ("Return".equalsIgnoreCase(dto.getType()) || "Increase".equalsIgnoreCase(dto.getType())) {
                warehouseDetail.setStock(warehouseDetail.getStock() + detailDto.getQuantity());
            } else if ("Decrease".equalsIgnoreCase(dto.getType())) {
                warehouseDetail.setStock(warehouseDetail.getStock() - detailDto.getQuantity());
            }

            warehouseDetailRepository.save(warehouseDetail); // guardar el cambio de stock
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
